package me.alexirving.core.newitem.placeholder

import me.alexirving.core.newitem.placeholder.PlaceHolder.Companion.formatLevel
import me.alexirving.core.utils.nBZ

class Static(
    private val levels: List<String>
) : PlaceHolder {
    override fun getAtLevel(level: Int): String {
        if (levels.isEmpty()) throw NoSuchElementException("One of the Static placeholders was empty!")

        val temp = (level - 1).nBZ()

        return (if (levels.size > temp)
            levels[temp]
        else
            levels[levels.size - 1]
                ).formatLevel(level)

    }
}