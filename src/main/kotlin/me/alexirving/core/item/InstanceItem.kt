/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * InstanceItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.printAsString
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * An instanceItem represents an item that could be in an inventory that will be modified.
 * This should be pooled and should be built when you
 */
class InstanceItem(val baseItem: BaseItem, private var refrence: InventoryReference) {
    private var templateItem: ItemStack = ItemStack(baseItem.material)
    private val placeholderLevels = mutableMapOf<String, Int>()

    init {
        buildTemplate()
    }

    fun buildToInventory() {
        refrence.inventory.addItem(templateItem.clone().apply {
            val toReplace = mutableMapOf<String, String>()
            for (s in baseItem.sections)
            replacePlaceHolders(this, toReplace)
        })
    }


    fun getReference() = refrence

    fun buildFromTemplate(placeholders: Map<String, String>) {
        refrence.setStack(templateItem.clone().apply {
            replacePlaceHolders(this, placeholders)
            val toReplace = mutableMapOf<String, String>()
            for (s in baseItem.sections) //Looping over sections
                for (attribute in s.value) //Looping over attributes
                    toReplace["$${attribute.id}$"] =
                        baseItem.placeholders[attribute.id]?.get(placeholderLevels[attribute.id] ?: 0) ?: ""
            replacePlaceHolders(this, toReplace)
        })
    }

    fun buildTemplate() {
        templateItem.itemMeta.printAsString()
        templateItem.itemMeta = templateItem.itemMeta.apply {
            displayName = baseItem.displayName
            lore = baseItem.lore
        }
        val nbtItem = NBTItem(templateItem)
        nbtItem.setUUID("uuid", UUID.randomUUID())
        templateItem = nbtItem.item
    }


    fun saveToItem(item: ItemStack) {
        NBTItem(item, true)
            .apply {
                setString("itemId", baseItem.id)
                setObject("attributes", placeholderLevels)
            }
    }

    private fun replacePlaceHolders(item: ItemStack, placeholders: Map<String, String>) {
        placeholders.printAsString("PLACEHOLDERS")
        val im = item.itemMeta
        for (pl in placeholders) {
            val k = "%${pl.key}%"
            im.lore = im.lore.map { it.replace(k, pl.value) }
            im.displayName = im.displayName.replace(k, pl.value)
        }
        item.itemMeta = im
    }
}
