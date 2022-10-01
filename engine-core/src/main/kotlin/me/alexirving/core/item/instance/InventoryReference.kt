package me.alexirving.core.item.instance

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.item.template.BaseItem
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * A reference to an item inside an inventory, this is used to be efficient however switching inventories has not been implemented.
 * This is also where the uuid of the item is stored, other then in the nbt data
 */
data class InventoryReference(var inventory: Inventory, private val baseItem: BaseItem, val id: UUID) {

    fun getStack(): ItemStack {
        for (i in inventory.contents)
            if (i == null)
                continue
            else
                if (i.type == baseItem.material) {
                    val f = NBTItem(i)
                    if (f.getUUID("uuid") == id)
                        return i
                }
        throw NotFoundException("Could not find ItemStack with uuid of $id for baseItem of ${baseItem.id}")
    }

    fun setStack(item: ItemStack) {
        for (i in inventory.contents.withIndex())
            if (i.value == null) continue
            else
                if (i.value.type == baseItem.material) {
                    val f = NBTItem(i.value)
                    if (!f.hasNBTData()) continue
                    if (!f.hasKey("uuid")) continue
                    if (f.getUUID("uuid") == id) {
                        inventory.setItem(i.index, item)
                        return
                    }
                }
        throw NotFoundException("Could not find ItemStack with uuid of $id for baseItem of ${baseItem.id}")
    }


    fun move(newInventory: Inventory): InventoryReference {
        this.inventory = newInventory
        return this
    }

    override fun toString(): String {
        return """{
            "Id": "${baseItem.id}",
            "uuid": "$id",
            "inventory": "$inventory"
            }""".trimMargin()
    }
}