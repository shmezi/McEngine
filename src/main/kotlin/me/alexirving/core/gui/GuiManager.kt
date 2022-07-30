package me.alexirving.core.gui

import me.alexirving.core.gui.instance.EngineInventory
import me.alexirving.core.gui.template.BaseInventory
import org.bukkit.entity.Player

object GuiManager {

    val guiHist = mutableMapOf<Player, MutableList<EngineInventory>>()

    fun back(vararg players: Player) {}

    fun close(vararg player: Player) {}

    fun open(id: String, vararg player: Player) {}


    fun back(players: List<Player>) {}

    fun close(player: List<Player>) {}

    fun open(id: String, player: List<Player>) {}
}