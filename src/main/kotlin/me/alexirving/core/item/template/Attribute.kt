package me.alexirving.core.item.template

open class Attribute(
    val id: String,
    val groups: List<String>,
    val max: Int,
    val placeholders: Map<String, String>,
)