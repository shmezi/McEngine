package me.alexirving.core.newitem

data class ItemData(
    val uuid: String,
    val baseId: String,
    val states: Map<String, Int>
)