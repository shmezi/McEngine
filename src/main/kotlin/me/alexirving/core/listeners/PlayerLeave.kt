package me.alexirving.core.listeners

import me.alexirving.core.EngineManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeave(val m: EngineManager) : Listener {
    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        m.unloadPlayer(e.player)
    }
}