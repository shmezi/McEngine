package me.alexirving.core.newitem.placeholder

class Dynamic(private val format: String) : PlaceHolder {


    override fun getAtLevel(level: Int) = format.format(level)
}