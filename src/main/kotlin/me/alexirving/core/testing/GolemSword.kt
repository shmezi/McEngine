package me.alexirving.core.testing

import me.alexirving.core.item.BaseItem
import me.alexirving.core.item.Modifier
import me.alexirving.core.item.Group
import me.alexirving.core.item.ga.ItemSlot
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GolemSword : BaseItem(ItemStack(Material.IRON_SWORD)) {
    init {
        val mg = Group("Enchants")
        addGroup(mg)
        val m = Modifier("sharpass")
        val slotMap = mutableMapOf<ItemSlot, List<String>>()
        slotMap[ItemSlot.PREFIX] = listOf("PREFIX")
        m.createLevel(slotMap)
        println("WORKED: ${mg.addModifier(m)}")
        addGroupModifier(mg, m)
    }
}