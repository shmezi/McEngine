package me.alexirving.core.item

import org.bukkit.Material

enum class BlockableItems(material: Material) {
    DIAMOND(Material.DIAMOND_BLOCK),
    IRON_INGOT(Material.IRON_BLOCK),
    REDSTONE_WIRE(Material.REDSTONE_BLOCK),
    EMERALD(Material.EMERALD_BLOCK),
    GOLD_INGOT(Material.GOLD_BLOCK);

    companion object {
        fun isBlockable(material: Material) = values().any { it.name == material.name }    }
}