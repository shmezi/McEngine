package me.alexirving.core.events

import me.alexirving.core.EngineManager
import me.alexirving.core.item.instance.EngineItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(val manager: EngineManager) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (manager.item.isCustom(e.player.itemInHand))
            EngineItem.of(manager, e.player.itemInHand, e.player.inventory)?.runStartEffects(e.player)
        manager.loadPlayer(e.player)
    }

}