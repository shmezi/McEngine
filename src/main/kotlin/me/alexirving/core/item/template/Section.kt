package me.alexirving.core.item.template

data class Section(
    val id: String,
    val attributes: List<Attribute>,
    val groups: List<String>
    )