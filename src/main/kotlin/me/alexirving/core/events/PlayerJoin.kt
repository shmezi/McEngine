package me.alexirving.core.events

import me.alexirving.core.EngineManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(val manager: EngineManager) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) = manager.eco.load(e.player.uniqueId)
}