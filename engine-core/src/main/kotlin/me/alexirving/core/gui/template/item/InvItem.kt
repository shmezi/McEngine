package me.alexirving.core.gui.template.item

import me.alexirving.core.gui.instance.EngineInventory

/**
 * Represents an item inside an inventory
 * @param dynamic Whether or not the item has placeholders that should be replaced
 * This is not the actual item that will be stored in an [EngineInventory], but a template. is it important to remember this.
 */
abstract class InvItem(
    val dynamic: Boolean = false
) {
    /**
     * Runs when an interaction occurs
     * @param states The current states of items, such a [SwitchItem]'s current stage.
     * It's not stored in the [SwitchItem] because of how it is merely a template item.
     * @param variables The custom variables set by the file, such as a list of items for a backpack.
     * */
    abstract fun run(variables: MutableMap<String, Any>, states: MutableMap<String, Int>)

    abstract fun getCurrentItem(states: MutableMap<String, Int>, variables: MutableMap<String, Any>)
}