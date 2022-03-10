package me.alexirving.core.commands

import me.alexirving.core.animation.utils.getFacing
import me.alexirving.core.item.objects.BaseItem
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

class Test : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        when (args[0]) {

            "A" -> {
                val i = BaseItem.loadFromJson(File("SuperPick.json")).buildInstance()

                player.sendMessage("You are facing: ${player.location.getFacing().name}")
            }
            "B" -> {
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