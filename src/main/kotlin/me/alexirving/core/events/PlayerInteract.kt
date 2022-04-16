package me.alexirving.core.events

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteract : Listener {
    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        if (!e.hasItem()) return
        val item = NBTItem(e.item)
        if (!item.hasNBTData()) return
        if (!item.hasKey("id")) return
//        MongoDb.getBackPack("ab74ad45-0ea9-4550-bb99-b8d822fbe909")
    }
}