package me.alexirving.core.gui

import me.alexirving.core.gui.item.GuiItem
import org.bukkit.event.inventory.InventoryType

/**
 * Represents a dynamically
 */
data class Gui(
    val id: String,
    val type: InventoryType,
    var rows: Int,
    var pages: Int,
    val mutable: MutableSet<Int>,
    val varItems: MutableMap<String, GuiItem>,
    val states: MutableMap<String, Short>
) {
}