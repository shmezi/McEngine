package me.alexirving.core.newitem

import me.alexirving.core.newitem.placeholder.PlaceHolder

data class BaseItem(
    val id: String,
    val name: String,
    val lore: List<String>,
    val material: String,
    val limit: Map<String, Int>,
    val properties: Map<ActionEvent, Boolean>,
    val placeholders: Map<String, PlaceHolder>,
    val sections: Map<String, List<Attribute>>
)