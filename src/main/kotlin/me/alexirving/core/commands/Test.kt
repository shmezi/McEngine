package me.alexirving.core.commands

import me.alexirving.core.animation.getFacing
import me.alexirving.core.testing.GolemSword
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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
                player.sendMessage("You are facing: ${player.location.getFacing().name}")
            }
        }

        return true
    }
}