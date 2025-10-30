package io.zee.pokedexevo.domain.repository

import io.zee.pokedexevo.domain.model.PokemonSummary

interface PokemonRepository {
    /**
     * Loads a page of Pokemon summaries from the API.
     * @param limit number of items per page (max 100 typically)
     * @param offset offset from the beginning
     */
    suspend fun getPokemonPage(limit: Int, offset: Int): List<PokemonSummary>
}