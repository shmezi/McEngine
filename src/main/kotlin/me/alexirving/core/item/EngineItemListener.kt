package me.alexirving.core.item

import me.alexirving.core.EngineManager
import me.alexirving.core.item.instance.EngineItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class EngineItemListener(val m: EngineManager) : Listener {
    @EventHandler
    fun onSwap(e: PlayerItemHeldEvent) {
        val p = e.player
        val profile = m.profile.getProfile(p)
        val pItem = p.inventory.getItem(e.previousSlot)
        val nItem = p.inventory.getItem(e.newSlot)
        if (m.item.isCustom(pItem)) {
            val ei = EngineItem.of(m, pItem, p.inventory) ?: return
            ei.runResetEffects(p)
            profile.activeEffects.clear()
        }
        if (m.item.isCustom(nItem)) {
            val ei = EngineItem.of(m, nItem, p.inventory) ?: return
            ei.runStartEffects(p)
        }
    }
}