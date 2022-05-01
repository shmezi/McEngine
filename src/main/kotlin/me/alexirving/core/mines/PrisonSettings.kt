package me.alexirving.core.mines

import java.util.*

data class PrisonSettings(
    var public: Boolean,
    var tax: Double,
    val whiteList: MutableList<UUID>,
    val banned: MutableList<UUID>,
    val sell: Boolean,
    val block: Boolean,
    val gang: UUID?
) {
    companion object {
        fun default(): PrisonSettings = PrisonSettings(
            false, 0.0, mutableListOf(), mutableListOf(),
            sell = false,
            block = false,
            null
        )
    }
}