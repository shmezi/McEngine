package me.alexirving.core.newitem

data class Attribute(
    val id: String,
    val max: Int,
    val placeholders: Map<String, Int>,
    val actions: List<Action>,
    val groups: Map<String, Int>
)