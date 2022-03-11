package me.alexirving.core.item

import me.alexirving.core.item.template.Attribute
import me.alexirving.core.item.template.BaseItem

class InstanceItem(val baseItem: BaseItem) {
    private val attributes = mutableMapOf<Attribute, Int>()

    fun getCurrentGroupCount(group: String): Int {
        var c = 0
        for (a in attributes.keys)
            if (a.groups.contains(group))
                c++
        return c
    }


    fun getAttribute(id: String): Attribute? {
        for (a in attributes.keys)
            if (a.id == id)
                return a
        return null
    }

    fun addAttribute(name: String): Boolean {
        val a = getAttribute(name) ?: return false
        for (g in a.groups)
            if (a.max < getCurrentGroupCount(name) + 1)
                return false
        return true
    }

}