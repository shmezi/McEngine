package me.alexirving.core.animation.cmds

import me.alexirving.core.McEngine
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class McEngineReloadCMD(val mcEngine: McEngine) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        mcEngine.reload()
        return true
    }
}