package io.zee.pokedexevo.data.repository

import io.zee.pokedexevo.data.remote.PokeApiService
import io.zee.pokedexevo.domain.model.PokemonSummary
import io.zee.pokedexevo.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val api: PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonPage(limit: Int, offset: Int): List<PokemonSummary> {
        val response = api.getPokemonList(limit = limit, offset = offset)
        return response.results.mapNotNull { item ->
            val id = item.url.extractPokemonIdOrNull() ?: return@mapNotNull null
            PokemonSummary(
                id = id,
                number = id,
                name = item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                imageUrl = officialArtworkUrl(id)
            )
        }
    }
}

private fun String.extractPokemonIdOrNull(): Int? = try {
    trimEnd('/').substringAfterLast('/').toInt()
} catch (_: Throwable) { null }

private fun officialArtworkUrl(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
