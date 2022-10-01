package me.alexirving.core.gui.instance

import me.alexirving.core.gui.template.BaseInventory
import org.bukkit.entity.Player

class EngineInventory(
    val base: BaseInventory,
    val viewers: MutableSet<Player>,
    val states: MutableMap<String, Int> = mutableMapOf()
) {
    val gui = base.template.create()


    fun open(vararg viewers: Player) {
        this.viewers.addAll(viewers)
        viewers.forEach {
            gui.open(it)
        }
    }

}