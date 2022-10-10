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
        m.user.get(p.uniqueId) {
            if (ItemManager.isCustom(pItem)) {
                val ei = EngineItem.of(m, pItem, p.inventory)
                ei?.runResetEffects(p)
            }
            if (ItemManager.isCustom(nItem)) {
                val ei = EngineItem.of(m, nItem, p.inventory)
                ei?.runStartEffects(p)
            }

        }

    }

    @EventHandler
    fun onMine(e: BlockBreakEvent) {
//        if (e.isCancelled) return
//        val player = e.player
//        val brokenType = e.block.type
//        if (player.hasPermission("engine.prison.autoblock"))
//            m.user.get(player.uniqueId) { user ->
//                if (user.settings.block && BlockableItems.isBlockable(brokenType) && e.player.inventory.contains(
//                        brokenType, 9)
//                ) {
//                    var counter = 9
//                    for (item in player.inventory.filter { it.type == brokenType }) {
//                        if (item.amount == counter)
//                            player.inventory.remove(item)
//                        else {
//                            val ia = item.amount
//                            item.amount -= counter.nBZ()
//                            counter -= ia
//                            if (item.amount > counter)
//                                break
//                        }
//                    }
//                    player.updateInventory()
//                }
//            }
    }
}