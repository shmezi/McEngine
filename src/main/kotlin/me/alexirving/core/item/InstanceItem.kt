package me.alexirving.core.item

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.template.Attribute
import me.alexirving.core.item.template.BaseItem
import org.bukkit.inventory.ItemStack

class InstanceItem(val baseItem: BaseItem, private val stack: ItemStack) {
    private val attributes = mutableMapOf<String, Int>()
    fun getCurrentGroupCount(group: String): Int {
        var c = 0
        for (s in baseItem.sections)
            c += s.attributes.filter { attributes.containsKey(it.id) && it.groups.contains(group) }.size
        return c
    }


    fun getAttribute(id: String): Attribute? {
        for (s in baseItem.sections)
            for (a in s.attributes)
                if (a.id == id)
                    return a
        return null
    }

    fun addAttribute(name: String): Boolean {
        val a = getAttribute(name) ?: return false

        return true
    }


    fun setLevel(id: String, value: Int): Boolean {
        attributes[id] ?: return false
        return if (value > 0 && value <= (getAttribute(id)?.max ?: 0)) {
            attributes[id] = value
            save()
            true
        } else false
    }

    fun levelUp(id: String): Boolean {
        attributes[id] ?: return false
        return if ((getAttribute(id)?.max ?: 0) > (attributes[id] ?: 0) + 1) {
            attributes[id] = (attributes[id] ?: 0) + 1
            save()
            false
        } else true
    }

    fun levelDown(id: String): Boolean {
        attributes[id] ?: return false
        return if ((getAttribute(id)?.max ?: 0) >= 1) {
            attributes[id] = (attributes[id] ?: 0) - 1
            save()
            true
        } else false
    }

    fun build() {

    }

    fun save() {
        NBTItem(stack, true)
            .apply {
                setString("itemId", baseItem.id)
                setObject("attribute", attributes)
            }
    }
}