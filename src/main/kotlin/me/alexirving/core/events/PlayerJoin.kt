package me.alexirving.core.events

import me.alexirving.core.econemy.EcoManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) = EcoManager.load(e.player.uniqueId)
}