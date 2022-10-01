package me.alexirving.core.gui.template

import dev.triumphteam.gui.components.GuiType
import dev.triumphteam.gui.guis.Gui
import me.alexirving.core.gui.template.item.InvItem
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

/**
 * Represents an inventory template
 * @param type Type of inventory
 * @param rows Amount of rows for the inventory
 * @param variables Variables to be used, and their default items if needed
 * @param slots Slots in the GUI, Note that a String is used for slot number this is because slot number can be 0-12 which is a range.
 * @param interactions The **Disabled** interactions for the inventory
 */
class BaseInventory(
     val id: String,
    private val type: GuiType,
    private val title: String,
    private val mutablity:Mutability,
    private val rows: Int,
    private val interactions: Set<GuiInteraction>,
    private val variables: MutableMap<String, Any> = mutableMapOf(),
    private val slots: MutableMap<String, InvItem> = mutableMapOf(),

) {
    companion object {
        private val regex = "^(\\d+)-(\\d+)\$".toPattern()
    }

    val template = Gui.gui().type(type).rows(rows).title(LegacyComponentSerializer.legacyAmpersand().deserialize(title))
        .apply { gui ->
            interactions.forEach { it.f(gui) }
            slots.forEach { entry ->
                val actualSlots = mutableListOf<Int>()
                val sections = entry.key.split(",")
                sections.forEach {
                    val n = it.toIntOrNull()
                    if (n != null)
                        actualSlots.add(n)
                    else {
                        val m = regex.matcher(it)
                        if (m.matches())
                            actualSlots.addAll(m.group(0).toInt()..m.group(1).toInt())
                    }
                }
                actualSlots.forEach {
//                    gui.setItem(it, entry.value.item)
                }
            }
        }


    fun asEngineInventory(player: Player) {

    }
}