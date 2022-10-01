package me.alexirving.core.newitem.placeholder

import me.alexirving.core.utils.toRoman

interface PlaceHolder {

    companion object {
        private val replaceRoman = "(?<!\\\\)\\*".toRegex()
        private val replaceNormal = "(?<!\\\\)\\\$".toRegex()
        private val replaceBackslash = "\\\\(?=[^\\\\,^ ])".toRegex()

        fun String.formatLevel(level: Int) =
            if (level <= 0) "" else this.replace(replaceNormal, level.toString())
                .replace(replaceRoman, level.toRoman())
                .replace(replaceBackslash, "")
    }


    fun getAtLevel(level: Int): String


}