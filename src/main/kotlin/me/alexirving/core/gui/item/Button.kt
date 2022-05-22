package me.alexirving.core.gui.item

import me.alexirving.core.actions.Action
import org.bukkit.entity.Player

class Button(override val id: String, val actions: Set<Action>) : GuiItem {
    override val typeId = "Button"
    override fun run(states: Map<String, Int>, player: Player) {
        actions.forEach {
            it.run(generateDataMap(player,states))
        }
    }
}