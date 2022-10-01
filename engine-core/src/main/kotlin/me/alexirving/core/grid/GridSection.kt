package me.alexirving.core.grid

import me.alexirving.core.actions.Action

open class GridSection(
    val priority: Int = 0,
    val actions: MutableList<Action>? = null,
    val area: MutableMap<UInt, GridSection>? = null,
    val builder: Boolean = false
) : Cloneable {

//    open fun compileBuilder(): Map<Int, GridSection> {}

//    open fun combile(): Map<Int,>
}