package me.alexirving.core.item

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.template.Attribute
import me.alexirving.core.item.template.BaseItem
import org.bukkit.inventory.ItemStack

/**
 * An instanceItem represents an item that could be in an inventory that will be modified.
 * This should be pooled and should be built when you
 */
class InstanceItem(val baseItem: BaseItem, private val stack: ItemStack) {
    private val attributes = mutableMapOf<String, Int>()
    private var templateCache: ItemStack = stack.clone()


    fun getCurrentGroupCount(group: String): Int {
        var c = 0
        for (s in baseItem.sections.values)
            for (a in s)
                if (a.id == group)
                    c++
        return c
    }


    fun getAttribute(id: String): Attribute? {
        for (s in baseItem.sections)
            for (a in s.value)
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



    fun build(placeholders: Map<String, String>): ItemStack {
        val im = templateCache.clone().itemMeta //Copying template to modify
        for (p in placeholders) {
            im.displayName.replace("{${p.key}}", p.value)
            for (l in im.lore)
                l.replace("{${p.key}}", p.value)
        }
        stack.itemMeta = im //Finally, applying changed template to the main item stack
        return stack
    }

    fun save() {
        NBTItem(stack, true)
            .apply {
                setString("itemId", baseItem.id)
                setObject("attributes", attributes)
            }
    }

    fun save(m: Boolean) {
        NBTItem(stack, true)
            .apply {
                if (m)
                    setString("itemId", baseItem.id)
                else
                    setObject("attributes", attributes)
            }
    }
}