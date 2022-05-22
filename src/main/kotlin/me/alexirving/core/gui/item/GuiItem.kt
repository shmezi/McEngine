package me.alexirving.core.gui.item

import org.bukkit.entity.Player

interface GuiItem {
    val typeId: String
    val id: String
    fun run(states: Map<String, Int>, player: Player)
    fun generateDataMap(player: Player, states: Map<String, Int>) = mutableMapOf<String, Any>().apply {
        this["player"] = player
        this["states"] = states
    }
}