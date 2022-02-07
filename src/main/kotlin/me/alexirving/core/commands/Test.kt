package me.alexirving.core.commands

import me.alexirving.core.item.Modifier
import me.alexirving.core.item.ga.ItemSlot
import me.alexirving.core.testing.GolemSword
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Test : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        when (args[0]) {
            "A" -> {
                val x = GolemSword().rebuild()
                player.inventory.addItem(x)
            }
            "B" -> {
                val item = GolemSword()
                val slotMap = mutableMapOf<ItemSlot, List<String>>()
                slotMap[ItemSlot.PREFIX] = listOf("&b")
                slotMap[ItemSlot.EXTRA_LORE] = listOf("This makes ur ass super sharp")

                val mod = Modifier("sharpass")
                mod.createLevel(slotMap)
                item.addGroupModifier(item.getGroup("Enchants")!!, mod)
                player.inventory.addItem(item.rebuild())
            }
        }

        return true
    }
}