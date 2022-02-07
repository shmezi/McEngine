package me.alexirving.core.item

import me.alexirving.core.item.ga.ItemSlot

class Modifier(val id: String) {
    private var current = 0
    private val modLevels = mutableListOf<MutableMap<ItemSlot, List<String>>>()// Level, Map<Slot, value>

    fun createLevel(slotMap: MutableMap<ItemSlot, List<String>>) {
        modLevels[modLevels.size + 1] = slotMap
    }

    fun levelUp(): MutableMap<ItemSlot, List<String>> {
        if ((current + 1) <= modLevels.size - 1) current++
        return build()
    }

    fun levelDown(): MutableMap<ItemSlot, List<String>> {
        if ((current - 1) >= 0) current--
        return build()
    }

    fun setLevel(value: Int): MutableMap<ItemSlot, List<String>>? {
        if (modLevels.size > value && value >= 0) {
            current = value
            return build()
        }
        return null
    }


    fun build(): MutableMap<ItemSlot, List<String>> {
        return modLevels[current]!!
    }
}