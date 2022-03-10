package me.alexirving.core.item.objects

data class Section(var description: String, var slot: ItemSlot) {
    var template = ""
    val groups = mutableListOf<String>()
    val attributes = mutableListOf<Attribute>()
}