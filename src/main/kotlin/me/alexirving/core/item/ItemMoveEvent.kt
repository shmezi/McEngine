package me.alexirving.core.item

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ItemMoveEvent(reference: InventoryReference) : Event() {
    companion object {
        val handlers: HandlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = ItemMoveEvent.handlers

}