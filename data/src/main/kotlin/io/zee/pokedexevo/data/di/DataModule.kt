package io.zee.pokedexevo.data.di

import io.zee.pokedexevo.data.remote.PokeApiService
import io.zee.pokedexevo.data.repository.PokemonRepositoryImpl
import io.zee.pokedexevo.domain.repository.PokemonRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val dataModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = false
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
    singleOf(::PokeApiService)
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}
