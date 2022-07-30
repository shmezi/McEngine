package me.alexirving.core.newitem.placeholder

import me.alexirving.core.utils.toRoman

interface PlaceHolder {

    companion object {
        private val replaceRoman = "(?<!\\\\)\\*".toRegex()
        private val replaceNormal = "(?<!\\\\)\\\$".toRegex()
        private val replaceBackSpace = "\\\\(?=[^\\\\,^ ])".toRegex()

        fun String.format(level: Int) =
            this.replace(replaceNormal, level.toString()).replace(replaceRoman, level.toRoman())
                .replace(replaceBackSpace, "")
    }


    fun getAtLevel(level: Int): String


}