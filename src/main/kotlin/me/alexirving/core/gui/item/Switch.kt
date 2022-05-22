package me.alexirving.core.gui.item

import me.alexirving.core.actions.Action
import me.alexirving.core.utils.nBZ
import org.bukkit.entity.Player

class Switch(override val id: String, val actions: List<Set<Action>>) : GuiItem {
    override val typeId = "Switch"
    override fun run(states: Map<String, Int>, player: Player) {
        actions[states[id].nBZ()].forEach {
            it.run(generateDataMap(player,states))
        }
    }
}