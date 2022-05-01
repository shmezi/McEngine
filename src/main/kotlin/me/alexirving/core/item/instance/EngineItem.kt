/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * InstanceItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.instance

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.EngineManager
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.item.AttributeAddResponse
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.nBZ
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Wraps an [ItemStack] for use of a baseItem, to change stuff like lore.
 */
class EngineItem {
    private val baseItem: BaseItem
    private var reference: InventoryReference
    private var templateItem: ItemStack
    private var isBuilt = false
    private val m: EngineManager

    constructor(m: EngineManager, baseItem: BaseItem, reference: InventoryReference, built: Boolean) {
        isBuilt = built // DO NOT FUCKING MOVE ANYWHERE U FUCKING MORON!
        this.baseItem = baseItem
        this.reference = reference
        this.m = m
        templateItem = baseItem.getTemplate()
        NBTItem(templateItem, true).apply {
            setUUID("uuid", reference.id)

        }
        if (isBuilt) {
            NBTItem(reference.getStack(), true).mergeCustomNBT(templateItem)
        }
    }

    constructor(m: EngineManager, baseItem: BaseItem, reference: InventoryReference) : this(
        m,
        baseItem,
        reference,
        false
    )


    /**
     * Gets an attribute's levels of the item (Key: “Section. Attribute ", Value: Level)
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

    private fun getGroupCount(): Map<String, Int> {
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
        for (a in getAttributeLevels()) { //Loop through attribute levels | Attributes are stored in nbt data in a fomrat of sectionId.attributeId
            val sectionId = a.key.substringBefore('.')
            val attributeId = a.key.substringAfter('.')
            val attribute = baseItem.sections[sectionId]?.firstOrNull { it.id == attributeId }
                ?: continue //Getting the attribute from the id in the loop
            for (p in attribute.placeholders) //Looping over placeholders, if the level is higher then previous itll be repalced
                if (levels.containsKey(p.key)) {
                    if ((levels[p.key] ?: continue) < p.value[(a.value - 1).nBZ()])
                        levels[p.key] = p.value[(a.value - 1).nBZ()]
                } else levels[p.key] = p.value[(a.value - 1).nBZ()]
        }
        for (l in levels) { //Declaring what needs to be replaced from the levels
            replacers[l.key] =
                if (baseItem.placeholders[l.key]!!.size > l.value)
                    baseItem.placeholders[l.key]!![l.value]
                else baseItem.placeholders[l.key]!![0]

        }
        for (r in this.baseItem.placeholders) //Removing blank placeholders TODO: Make all placeholders start from 0 to allow use of 0 for here.
            if (!replacers.containsKey(r.key))
                replacers[r.key] = ""
        return replacers
    }

    private fun getEffectLevels(): MutableMap<String, Int> {
        val levels = mutableMapOf<String, Int>()
        for (a in getAttributeLevels()) {
            val sectionId = a.key.substringBefore('.')
            val attributeId = a.key.substringAfter('.')
            val attribute = baseItem.sections[sectionId]?.firstOrNull { it.id == attributeId } ?: continue
            attribute.effects[(a.value - 1).nBZ()].forEach {
                if (!levels.containsKey(it.key) || it.value > (levels[it.key] ?: 0))
                    levels[it.key] = it.value
            }
        }
        return levels
    }

    fun forceSetLevel(section: String, attribute: String, level: Int): EngineItem {
        val n = NBTItem(templateItem, true)
        var m = mutableMapOf<String, Int>()
        if (!n.hasKey("attributes")) {
            if (level == 0) return this

            m["$section.$attribute"] = level

        } else {
            m = n.getObject("attributes", MutableMap::class.java) as MutableMap<String, Int>
            if (level == 0)
                m.remove("$section.$attribute")
            else
                m["$section.$attribute"] = level
        }
        n.setObject("attributes", m)
        return this
    }

    fun levelUp(section: String, attribute: String): AttributeAddResponse {
        val level = getAttributeLevels()["$section.$attribute"] ?: 0

        val a = baseItem.sections[section]?.firstOrNull { it.id == attribute }
            ?: throw NotFoundException("$attribute is not an upgrade that can be done on item: `${baseItem.id}`")

        when {
            level == 0 ->
                for (group in a.groups)
                    if (/*Current group count + 1*/(getGroupCount()[group] ?: 0) + 1 > (baseItem.groups[group]
                            ?: 0)/*Max allowed of group*/)
                        return AttributeAddResponse.GROUP
            level + 1 > a.max -> return AttributeAddResponse.MAX
        }

        forceSetLevel(section, attribute, level + 1)
        return AttributeAddResponse.SUCCESSFUL
    }

    fun levelDown(section: String, attribute: String): AttributeAddResponse {
        val level = getAttributeLevels()["$section.$attribute"] ?: 0
        if (level == 0)
            return AttributeAddResponse.MIN
        forceSetLevel(section, attribute, level - 1)
        return AttributeAddResponse.SUCCESSFUL
    }

    fun getLevel(section: String, attribute: String): Int {
        val n = NBTItem(templateItem)
        if (!n.hasNBTData()) return 0
        if (!n.hasKey("attributes")) return 0
        return (n.getObject("attributes", MutableMap::class.java) as MutableMap<String, Int>)["$section.$attribute"]
            ?: 0
    }


    /**
     * Adds an enchantment to the template item
     */
    fun addEnchant(enchant: Enchantment, level: Int) {
        templateItem.addEnchantment(enchant, level)
    }


    /**
     * Returns an [ItemStack] of the instanceItem using the template.
     */
    fun build(): ItemStack {
        for (e in getEffectLevels()) //Don't move to allow editing of template before cloning it :)
            m.effect.onBuild(this, m.effect.getEffectById(e.key) ?: continue, e.value)

        val ti = templateItem.clone().apply {
            replacePlaceHolders(this, getReplacements())
        }

        return ti
    }

    fun runStartEffects(p: Player) {
        m.user.getUser(p) {
            for (e in getEffectLevels()) {
                val effect = m.effect.getEffectById(e.key) ?: continue
                m.effect.onStart(p, effect, e.value)
                it.activeEffects[effect] = e.value
            }
        }

    }

    fun runResetEffects(p: Player) {
        m.user.getUser(p) {
            for (e in getEffectLevels()) {
                m.effect.onReset(p, m.effect.getEffectById(e.key) ?: continue)
            }
        }
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
    private val ma = "\\{\\{(.+)}}".toRegex()
    private val pma = ma.toPattern()
    private fun replacePlaceHolders(item: ItemStack, placeholders: Map<String, String>) {
        val im = item.itemMeta
        for (pl in placeholders) {
            val k = "%${pl.key}%"
            im?.lore = im?.lore?.map { it.replace(k, pl.value) }
            im?.setDisplayName(im.displayName.replace(k, pl.value))
        }
        item.itemMeta = im
    }


    companion object {
        fun of(m: EngineManager, item: ItemStack?, inventory: Inventory): EngineItem? {
            val nbt = NBTItem(item ?: return null)
            if (!nbt.hasNBTData()) return null
            val a = m.item.bases[nbt.getString("id")] ?: return null
            return EngineItem(m, a, InventoryReference(inventory, a, nbt.getUUID("uuid")), true)
        }
    }
}