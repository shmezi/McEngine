package me.alexirving.core.grid

import org.bukkit.entity.Player

class GridInstance : GridSection() {
    val viewers: MutableSet<Player> = mutableSetOf()

    val states = mutableMapOf<String, Int>()

    val variables = mutableMapOf<String, Any>()

    fun openAll() {

    }

    fun addAndOpenTo() {}

    fun addAndOpenAll() {}
}