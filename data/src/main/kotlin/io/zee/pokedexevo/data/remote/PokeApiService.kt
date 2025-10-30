package io.zee.pokedexevo.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://pokeapi.co/api/v2"

class PokeApiService(private val client: HttpClient) {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return client.get("$BASE_URL/pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }
}

// --- DTOs ---
@kotlinx.serialization.Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NameUrl>
)

@kotlinx.serialization.Serializable
data class NameUrl(
    val name: String,
    val url: String
)
