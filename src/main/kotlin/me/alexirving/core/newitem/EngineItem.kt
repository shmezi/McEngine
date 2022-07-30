package me.alexirving.core.newitem

import me.alexirving.core.utils.replacePH
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Represents an itemstack that is currently somewhere in the game.
 */
class EngineItem(
    val template: BaseItem,
    private var location: Inventory,
    private var data: ItemData
) {
    private var itemCache: ItemStack

    private var placeholderCache: Map<String, String>
    private var material: Material

    init {
        itemCache = build()
        placeholderCache = reloadReplacements()
        material = reloadMaterial()
    }

    private fun reloadReplacements(): MutableMap<String, String> {
        val replacers = mutableMapOf<String, String>()
        template.placeholders.forEach {
            replacers[it.key] = it.value.getAtLevel(data.states[it.key] ?: 0)
        }
        return replacers
    }

    private fun reloadMaterial() = Material.valueOf(template.material.replacePH(reloadReplacements()))

    private fun reloadCache() {
        reloadMaterial()
        reloadReplacements()
    }

    fun build(): ItemStack {
        reloadCache()
        return ItemStack(Material.ACACIA_BOAT)
    }

}