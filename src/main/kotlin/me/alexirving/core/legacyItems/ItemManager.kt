package me.alexirving.core.legacyItems
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack

class ItemManager {
    private val loadedItems = HashMap<String, ItemStack>()

    fun reload(vararg itemConfigs: FileConfiguration): ItemManager {
        loadedItems.clear()
        for (config in itemConfigs)
            load(config)
        return this
    }

    private fun load(itemsConfig: FileConfiguration) {
//        for (item in itemsConfig.getKeys(false))
//            loadedItems[item] = buildItem(itemsConfig.getConfigurationSection(item)!!)

    }


    private fun buildItem(itemSection: ConfigurationSection): ItemStack {
//        val builder = ItemBuilder(Material.valueOf(itemSection.getString("Material")!!.uppercase()))
//        builder.setDisplayName(itemSection.getString("Name") ?: "null")
//            .setLore(itemSection.getStringList("Lore") as ArrayList<String?> ?: ArrayList())
//            .setAmount(itemSection.getInt("Amount") ?: 1)
//            .setGlow(itemSection.getBoolean("Glow") ?: false)
//            .setHead(itemSection.getString("Head") ?: "shmezi")
//            .setBannerColor(DyeColor.valueOf(itemSection.getString("Color") ?: "WHITE"))
//            .setColor(itemSection.getColor("Color") ?: Color.WHITE)
//        return builder.build()
        return null!!
    }

    fun getItem(name: String): ItemStack {

        return loadedItems[name] ?: ItemStack(Material.STONE)
    }

}