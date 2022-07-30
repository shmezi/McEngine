package me.alexirving.core.item

import org.bukkit.Material

/**
 * Blockable items are items that could be converted into a more compacted item.
 *
 * For example:
 * IRON_INGOT -> IRON_BLOCK
 */
enum class BlockableItems(val convertsTo: Material) {
    DIAMOND(Material.DIAMOND_BLOCK),
    IRON_INGOT(Material.IRON_BLOCK),
    REDSTONE_WIRE(Material.REDSTONE_BLOCK),
    EMERALD(Material.EMERALD_BLOCK),
    GOLD_INGOT(Material.GOLD_BLOCK);

    companion object {
        fun isBlockable(material: Material) = values().any { it.name == material.name }
    }
}