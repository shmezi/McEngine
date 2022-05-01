package me.alexirving.core.item

import me.alexirving.core.EngineManager
import me.alexirving.core.item.instance.EngineItem
import me.alexirving.core.utils.nBZ
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerItemHeldEvent

class EngineItemListener(val m: EngineManager) : Listener {
    @EventHandler
    fun onSwap(e: PlayerItemHeldEvent) {
        val p = e.player
        val pItem = p.inventory.getItem(e.previousSlot)
        val nItem = p.inventory.getItem(e.newSlot)
        m.user.getUser(p.uniqueId) {
            if (m.item.isCustom(pItem)) {
                val ei = EngineItem.of(m, pItem, p.inventory)
                ei?.runResetEffects(p)
            }
            if (m.item.isCustom(nItem)) {
                val ei = EngineItem.of(m, nItem, p.inventory)
                ei?.runStartEffects(p)
            }

        }

    }

    @EventHandler
    fun onMine(e: BlockBreakEvent) {
        if (e.isCancelled) return
        val p = e.player
        val t = e.block.type
        if (p.hasPermission("engine.prison.autoblock"))
            m.user.getUser(p) { user ->
                if (user.settings.block && BlockableItems.isBlockable(t) && e.player.inventory.contains(
                        t,
                        9
                    )
                ) {
                    var counter = 9
                    for (item in p.inventory.filter { it.type == t }) {
                        if (item.amount == counter)
                            p.inventory.remove(item)
                        else {
                            val ia = item.amount
                            item.amount -= counter.nBZ()
                            counter -= ia
                            if (item.amount > counter)
                                break
                        }
                    }
                    p.updateInventory()
                }
            }

    }
}