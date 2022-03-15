/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngineReloadCMD.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

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