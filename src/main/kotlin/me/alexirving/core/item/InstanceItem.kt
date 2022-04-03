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
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Wraps an [ItemStack] for use of a baseItem, to change stuff like lore.
 */
class InstanceItem {
    val baseItem: BaseItem
    private var reference: InventoryReference
    private val templateItem: ItemStack
    private var exists = false

    constructor(baseItem: BaseItem, reference: InventoryReference) : this(baseItem, reference, false)

    constructor(baseItem: BaseItem, reference: InventoryReference, built: Boolean) {
        this.baseItem = baseItem
        this.reference = reference
        templateItem = baseItem.getTemplate()
        NBTItem(templateItem, true).apply { setUUID("uuid", reference.id) }
        exists = built
    }


    /**
     * Builds the template for an item instance to be built upon
     */


    fun getAttributes(): Map<String, Double> {
        return if (exists)
            (NBTItem(reference.getStack()).getObject("attributes", Map::class.java) as Map<String, Double>?)
                ?: mutableMapOf()
        else
            mutableMapOf()
    }

    fun getLevel(key: String): Int {
        return if (exists) {
            getAttributes()[key]?.toInt() ?: 0
        } else
            0
    }


    /**
     * Returns an [ItemStack] of the instanceItem using the template.
     */
    fun build() = templateItem.clone().apply {
        val replacements = mutableMapOf<String, String>()
        baseItem.placeholders.forEach {
            val l = getLevel(it.key) ?: 0
            replacements[it.key] = if (it.value.size > getLevel(it.key) ?: 0) it.value[l].replace("%", "$l") else "null"
        }
        replacePlaceHolders(this, replacements)
    }

    fun setLevel(placeholder: String, level: Int) {
        val a = getAttributes().toMutableMap()
        a[placeholder] = level.toDouble()
        NBTItem(reference.getStack(), true).setObject("attributes", a)
    }

    /**
     * Builds to the reference inventory!
     */
    fun buildToInventory() {
        reference.inventory.addItem(build())
        exists = true
    }

    /**
     * Updates the ItemStack in the reference item
     */
    fun updateItem() = reference.setStack(build())

    /**
     * Replaces all the placeholders of an item
     */
    private fun replacePlaceHolders(item: ItemStack, placeholders: Map<String, String>) {
        val im = item.itemMeta
        for (pl in placeholders) {
            val k = "%${pl.key}%"
            im.lore = im.lore.map { it.replace(k, pl.value) }
            im.displayName = im.displayName.replace(k, pl.value)
        }
        item.itemMeta = im
    }


    companion object {
        fun of(item: ItemStack, inventory: Inventory): InstanceItem? {
            val nbt = NBTItem(item)
            if (!nbt.hasNBTData()) return null
            val a = ItemManager.bases[nbt.getString("id")] ?: return null
            return InstanceItem(a, InventoryReference(inventory, a, nbt.getUUID("uuid")), true)
        }
    }
}
