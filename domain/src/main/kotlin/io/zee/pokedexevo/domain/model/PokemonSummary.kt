package io.zee.pokedexevo.domain.model

/**
 * Minimal info needed for list of Pokemon.
 */
data class PokemonSummary(
    val id: Int,
    val number: Int,
    val name: String,
    val imageUrl: String,
)
