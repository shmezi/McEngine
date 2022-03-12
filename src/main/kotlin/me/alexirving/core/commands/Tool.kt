package me.alexirving.core.commands

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.ItemManager
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Tool : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        when (args[0].lowercase()) {

            "getnbt" -> {
                player.sendMessage(NBTItem(player.itemInHand).toString())
            }
            "setnbt" -> {
                NBTItem(player.itemInHand, true).setString(args[1], args[2])
            }
            "getitem" -> {
                player.inventory.addItem(ItemManager.bases["SuperPick"]?.asInstance()?.build(mapOf()))
            }
            "b" -> {
                player.sendMessage(
                    "Block selected is facing ${
                        player.getTargetBlock(setOf(Material.AIR), 10)
                    }"
                )
            }
        }

        return true
    }
}