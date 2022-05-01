package me.alexirving.core.points.track

data class Level(
    val id: String,
    val name: String,
    val cmds: MutableList<String>,
    val price: Double
)