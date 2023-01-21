package me.alexirving.prison.mines

import java.util.*

data class PrisonSettings(
    var public: Boolean = false,
    var tax: Double = 0.0,
    val whiteList: MutableList<UUID> = mutableListOf(),
    val banned: MutableList<UUID> = mutableListOf(),
    val sell: Boolean = false,
    val block: Boolean = false,
    var gang: UUID? = null
)