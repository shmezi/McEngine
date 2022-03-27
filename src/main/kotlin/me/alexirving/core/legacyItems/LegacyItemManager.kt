/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * ItemManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.legacyItems

import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack

class LegacyItemManager {
    private val loadedItems = HashMap<String, ItemStack>()

    fun reload(vararg itemConfigs: FileConfiguration): LegacyItemManager {
        loadedItems.clear()
        for (config in itemConfigs)
            load(config)
        return this
    }

    private fun load(itemsConfig: FileConfiguration) {
        for (item in itemsConfig.getKeys(false))
            loadedItems[item] = buildItem(itemsConfig.getConfigurationSection(item) ?: continue)

    }


    private fun buildItem(itemSection: ConfigurationSection): ItemStack {
//        val builder = ItemBuilder(Material.valueOf(itemSection.getString("Material")!!.uppercase()))
//        builder.setDisplayName(itemSection.getString("Name") ?: "null")
//            .setLore(itemSection.getStringList("Lore") as ArrayList<String?> ?: ArrayList())
//            .setAmount(itemSection.getInt("Amount") ?: 1)
//            .setGlow(itemSection.getBoolean("Glow") ?: false)
//            .setHead(itemSection.getString("Head") ?: "notch")
////            .setBannerColor(DyeColor.valueOf(itemSection.getString("Color") ?: "WHITE"))
//            .setColor(itemSection.getColor("Color") ?: Color.WHITE)
//        return builder.build()
        return ItemStack(Material.STONE)
    }

    fun getItem(name: String): ItemStack {

        return loadedItems[name] ?: ItemStack(Material.STONE)
    }

}