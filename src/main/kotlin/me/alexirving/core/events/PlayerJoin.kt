package me.alexirving.core.events

import me.alexirving.core.EngineManager
import me.alexirving.core.item.instance.EngineItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(val manager: EngineManager) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player
        if (manager.item.isCustom(p.inventory.itemInMainHand))
            EngineItem.of(manager, p.inventory.itemInMainHand, p.inventory)?.runStartEffects(p)
        manager.loadPlayer(p)

    }

}