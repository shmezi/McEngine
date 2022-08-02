package me.alexirving.core.newitem

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.utils.getOrFirst
import me.alexirving.core.utils.getOrLast
import me.alexirving.core.utils.replacePH
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Represents an itemstack that is currently somewhere in the game.
 *
 * Note: c prefix is used to show it is a cache.
 */
class EngineItem(
    private val manager: ItemManager,
    private val template: BaseItem,
    var data: ItemData
) {
    private var cItem: ItemStack? = null

    private var cReplacements = mutableMapOf<String, String>()
    private var cMaterial = Material.AIR
    private var cAttributeLevels = mutableMapOf<String, Int>()

    private fun getLevel(section: String, attribute: String) = data.levels[section]?.get(attribute) ?: 0
    private fun setLevel(section: String, attribute: String, value: Int) {
        data.levels.getOrPut(section) { mutableMapOf() }[attribute] = value
    }


    /**
     * This is built once and modified as the item is changed.
     */
    private var groupLevels = mutableMapOf<String, Int>()

    init {
        recalculateGroups()
        buildItem()

    }

    private fun recalculateGroups() {
        for (section in data.levels) {
            template.sections[section.key]?.forEach { attribute ->
                val level = getLevel(section.key, attribute.key)
                attribute.value.groups.getOrLast(level).forEach {
                    groupLevels[it.key] = groupLevels.getOrPut(it.key) { 0 } + it.value
                }
            }
        }
    }

    fun groupLimitAdd(section: String, attribute: String): Boolean {
        val level = getLevel(section, attribute)

        template.sections[section]?.get(attribute)?.groups?.getOrLast(level)?.forEach {
            val l = groupLevels.getOrPut(it.key) { 0 } + it.value
            if ((template.groupLimits[it.key] ?: 0) < l) return false
            else groupLevels[it.key] = l
        }
        return true
    }

    fun groupLimitRemove(section: String, attribute: String) {
        val level = getLevel(section, attribute)

        template.sections[section]?.get(attribute)?.groups?.getOrFirst(level - 1)?.forEach {
            groupLevels[it.key] = groupLevels.getOrPut(it.key) { 0 } - it.value
        }
    }

    private fun buildPlaceholderLevels(): Map<String, Int> {
        val levels = mutableMapOf<String, Int>()
        fun set(id: String, value: Int) {
            if (levels.getOrPut(id) { 0 } < value) levels[id] = value
        }
        for (section in data.levels) {
            template.sections[section.key]?.forEach { attribute ->
                attribute.value.placeholders.forEach { placeholderLevels ->
                    placeholderLevels.forEach {
                        if (it.value == -1) set(it.key, section.value[attribute.value.id] ?: 0)
                        else set(it.key, it.value)
                    }

                }
            }
        }
        return levels
    }


    private fun buildReplacements(replacements: Map<String, Int> = cAttributeLevels): MutableMap<String, String> {

        val replacers = mutableMapOf<String, String>()
        template.placeholders.forEach {
            replacers["%${it.key}%"] = it.value.getAtLevel(replacements[it.key] ?: 0)
        }
        val ownerUUID = data.ownerUUID
        if (ownerUUID != null) {
            replacers["%player%"] = Bukkit.getOfflinePlayer(ownerUUID).name ?: ""
            replacers["%uuid%"] = ownerUUID.toString()
        }
        cReplacements = replacers
        return replacers
    }


    fun upgrade(section: String, attribute: String): Boolean {
        val level = getLevel(section, attribute)
        val limit = template.getMaxAttributeLevel(section, attribute)
        if (level + 1 > limit && limit != -1) return false
        if (!groupLimitAdd(section, attribute)) return false
        setLevel(section, attribute, level + 1)
        buildItem()
        return true
    }

    fun downgrade(section: String, attribute: String): Boolean {
        val level = getLevel(section, attribute)
        if (level <= 0) return false
        groupLimitRemove(section, attribute)
        setLevel(section, attribute, level - 1)
        buildItem()
        return true
    }

    private fun buildMaterial(): Material {
        val m = Material.valueOf(template.material.replacePH(cReplacements))
        cMaterial = m
        return m
    }


    private fun buildItem(): ItemStack {
        buildReplacements(buildPlaceholderLevels())
        buildMaterial() //Replacement cache needs to built before material can be parsed.

        val item = ItemStack(cMaterial)
        item.itemMeta = item.itemMeta.apply {
            this ?: return@apply
            this.setDisplayName(template.name.replacePH(cReplacements))
            this.lore = template.lore.map { it.replacePH(cReplacements) }
        }
        NBTItem(item, true).setUUID("uuid", data.uuid)
        cItem = item
        return item
    }

    fun getStack() = cItem

}