package me.alexirving.core.item.objects

data class Attribute(var id: String) {
    var min = 0
    var max = 0
    var description = ""
    var template = ""
    val groups = mutableListOf<String>()
    val levels = mutableListOf<String>()
}