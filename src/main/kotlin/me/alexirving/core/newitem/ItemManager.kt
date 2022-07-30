package me.alexirving.core.newitem

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class ItemManager : Listener {
    val itemsCache = mutableMapOf<UUID, EngineItem>()



    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        e.hand ?: return
    }
}