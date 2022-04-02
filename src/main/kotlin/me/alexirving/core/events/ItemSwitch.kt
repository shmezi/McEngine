package me.alexirving.core.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class ItemSwitch : Listener {
    @EventHandler
    fun onItemSwitch(e: PlayerItemHeldEvent) {
        val n = e.player.inventory.getItem(e.newSlot)
        if (n.type == Material.AIR) return

    }
}