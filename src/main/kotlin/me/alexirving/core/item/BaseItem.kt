package me.alexirving.core.item

import me.alexirving.core.item.ga.ItemSlot
import org.bukkit.inventory.ItemStack

abstract class BaseItem(private val item: ItemStack) : Cloneable {
    private val groups = arrayListOf<Group>()

    fun addGroup(group: Group) {
        groups.add(group)
    }

    fun removeGroup(group: Group) {
        groups.remove(group)
    }

    fun addGroupModifier(group: Group, mod: Modifier) {
        group.addModifier(mod)
    }

    fun removeGroupModifier(group: Group, mod: Modifier) {
        group.removeModifier(mod)
    }

    fun rebuild(): ItemStack {
        val meta = item.itemMeta!!
        val extras = mutableListOf<String>()
        for (group in groups) { //Listing through the groups
            for (modifier in group.getModifiers().values) { //Looping through modifiers
                for (set in modifier.build()) { //Looping through different slots.
                    for (value in set.value) //Looping through values in slot.
                        when (set.key) {
                            ItemSlot.LORE -> meta.lore!!.add(value)
                            ItemSlot.EXTRA_LORE -> extras.add(value)
                            ItemSlot.PREFIX -> meta.setDisplayName("${value}${meta.displayName}")
                            ItemSlot.SUFFIX -> meta.setDisplayName("${meta.displayName}${value}")
                        }

                }
            }
        }
        for (extra in extras)
            meta.lore!!.add(extra)
        return item
    }

    fun getGroup(name: String): Group? {
        for (group in groups)
            if (group.name == name)
                return group
        return null
    }

    fun getItem(): ItemStack {
        return item
    }

}