/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Command.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.Action
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player

class Command(args: Map<String, String>) : Action(args) {

    override val id = "command"
    override fun run(data: MutableMap<String, Any>) {
        for (p in data["players"] as Set<Player>)
            getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                (args["command"] as String?)?.replace("%player%", p.name) ?: ""
            )
    }
}