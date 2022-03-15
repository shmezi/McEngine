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
import org.bukkit.inventory.ItemStack

/**
 * An instanceItem represents an item that could be in an inventory that will be modified.
 * This should be pooled and should be built when you
 */
class InstanceItem(val baseItem: BaseItem, private var refrence: ItemStack) {
    private val templateItem: ItemStack = ItemStack(baseItem.material)

    private val attributeLevels = mutableMapOf<String, Int>()


    init {
        buildTemplate()
    }

    fun getReference() = refrence
    fun buildFromTemplate(placeholders: Map<String, String>?) {
        refrence =
            templateItem.clone().apply {
                replacePlaceHolders(this, placeholders)
                val toReplace = mutableMapOf<String, String>()
                for (s in baseItem.sections) //Looping over sections
                    for (attribute in s.value) //Looping over attributes
                        toReplace["$${attribute.id}$"] =
                            baseItem.placeholders[attribute.id]?.get(attributeLevels[attribute.id] ?: 0) ?: ""
                replacePlaceHolders(this, toReplace)
            }
    }

    fun buildTemplate() {
        templateItem.setItemMeta(templateItem.itemMeta.apply {
            displayName = baseItem.displayName
            lore = baseItem.lore
        })
    }


    fun saveToItem(item: ItemStack) {
        NBTItem(item, true)
            .apply {
                setString("itemId", baseItem.id)
                setObject("attributes", attributeLevels)
            }
    }

    private fun replacePlaceHolders(item: ItemStack, placeholders: Map<String, String>?) {
        item.apply {
            itemMeta.apply {
                fun rp(s: Char) {
                    for (p in baseItem.placeholders) {
                        displayName.replace("$s$${p.key}$s", placeholders?.get(p.key) ?: "")
                        for (l in lore)
                            l.replace("$s${p.key}$s", placeholders?.get(p.key) ?: "")
                    }
                }
                rp('$')
                rp('%')

            }


        }
    }
}