/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * InstanceItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.instance

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.AttributeAddResponse
import me.alexirving.core.item.ItemManager
import me.alexirving.core.item.template.BaseItem
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Wraps an [ItemStack] for use of a baseItem, to change stuff like lore.
 */
class ItemInstance {
    val baseItem: BaseItem
    private var reference: InventoryReference
    private var templateItem: ItemStack
    private var isBuilt = false

    constructor(baseItem: BaseItem, reference: InventoryReference, built: Boolean) {
        this.baseItem = baseItem
        this.reference = reference
        templateItem = baseItem.getTemplate()
        NBTItem(templateItem, true).apply {
            setUUID("uuid", reference.id)

        }
        NBTItem(reference.getStack(), true).mergeNBT(templateItem)
        isBuilt = built
    }

    constructor(baseItem: BaseItem, reference: InventoryReference) : this(baseItem, reference, false)


    /**
     * Gets the attribute levels of the item (Key: "Section.Attribute", Value: Level)
     */
    private fun getAttributeLevels(): Map<String, Int> {
        val a = if (isBuilt) {
            (NBTItem(templateItem).getObject(
                "attributes",
                Map::class.java
            ) as Map<String, Double>?)?.mapValues { it.value.toInt() } ?: mutableMapOf()
        } else mutableMapOf()

        return a
    }

    private fun getActiveAttributes(): List<String> {
        val a = if (isBuilt) {
            (NBTItem(templateItem).getObject(
                "attributes",
                Map::class.java
            ) as Map<String, Double>?)?.map { it.key }
                ?: mutableListOf()
        } else mutableListOf()

        return a
    }

    private fun getActiveSections(): Set<String> {
        return getActiveAttributes().map { it.substringBefore('.') }.toSet()
    }

    fun getGroupCount(): Map<String, Int> {
        val gc = mutableMapOf<String, Int>()
        fun add(key: String) = if (gc.containsKey(key)) gc[key] = gc[key]!! + 1 else gc[key] = 1
        for (a in getAttributeLevels())
            baseItem.sections[a.key.substringBefore('.')]?.firstOrNull { it.id == a.key.substringAfter('.') }?.groups?.forEach {
                add(it)
            }
        return gc
    }


    private fun getReplacements(): Map<String, String> {
        val replacers = mutableMapOf<String, String>()
        val levels = mutableMapOf<String, Int>()
        for (a in getAttributeLevels()) {
            val sectionId = a.key.substringBefore('.')
            val attributeId = a.key.substringAfter('.')
            val base = baseItem.sections[sectionId]?.firstOrNull { it.id == attributeId } ?: continue
            for (p in base.placeholders)
                if (levels.containsKey(p.key)) {
                    if ((levels[p.key] ?: continue) < p.value[a.value])
                        levels[p.key] = p.value[a.value]
                } else levels[p.key] = p.value[a.value]
        }
        for (l in levels) {
            replacers[l.key] =
                if (baseItem.placeholders[l.key]!!.size > l.value)
                    baseItem.placeholders[l.key]!![l.value]
                else baseItem.placeholders[l.key]!![0]
        }

        return replacers
    }

    fun setLevel(section: String, attribute: String, level: Int) {
        val n = NBTItem(templateItem, true)

            if (!n.hasKey("attributes")) {
                val m = mutableMapOf<String, Int>()
                m["$section.$attribute"] = level
                n.setObject("attributes", m)
            } else {
                val m = n.getObject("attributes", MutableMap::class.java) as MutableMap<String, Int>
                m["$section.$attribute"] = level
                n.setObject("attributes", m)
            }
    }

    fun getLevel(section: String, attribute: String): Int {
        val n = NBTItem(templateItem)
        if (!n.hasNBTData()) return 0
        if (!n.hasKey("attributes")) return 0
        return (n.getObject("attributes", MutableMap::class.java) as MutableMap<String, Int>)["$section.$attribute"]
            ?: 0
    }


    /**
     * Returns an [ItemStack] of the instanceItem using the template.
     */
    fun build() = templateItem.clone().apply {
        replacePlaceHolders(this, getReplacements())
    }


    /**
     * Builds to the reference inventory!
     */
    fun buildToInventory() {
        reference.inventory.addItem(build())
        isBuilt = true
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
        fun of(item: ItemStack, inventory: Inventory): ItemInstance? {
            val nbt = NBTItem(item)
            if (!nbt.hasNBTData()) return null
            val a = ItemManager.bases[nbt.getString("id")] ?: return null
            return ItemInstance(a, InventoryReference(inventory, a, nbt.getUUID("uuid")), true)
        }
    }
}