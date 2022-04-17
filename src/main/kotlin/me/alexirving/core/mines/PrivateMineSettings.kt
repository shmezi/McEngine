package me.alexirving.core.mines

import java.util.*

data class PrivateMineSettings(
    var public: Boolean,
    var tax: Double,
    val whiteList: MutableList<UUID>,
    val banned: MutableList<UUID>
) {
    companion object {
        fun default(): PrivateMineSettings = PrivateMineSettings(false, 0.0, mutableListOf(), mutableListOf())
    }
}