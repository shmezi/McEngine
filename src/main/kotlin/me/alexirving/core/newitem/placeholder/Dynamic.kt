package me.alexirving.core.newitem.placeholder

import me.alexirving.core.newitem.placeholder.PlaceHolder.Companion.formatLevel

class Dynamic(private val format: String) : PlaceHolder {


    override fun getAtLevel(level: Int) = format.formatLevel(level)
}