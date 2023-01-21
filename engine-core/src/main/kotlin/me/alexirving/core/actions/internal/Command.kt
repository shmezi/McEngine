/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Command.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.Action
import me.alexirving.core.actions.data.ActionData
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer

class Command(args:ActionData) : Action(args) {

    override val id = "command"
    override fun run(data: ActionData) {

        for (p in data["players"]?.asPlayers() ?: return) {
            getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                (args["command"] as String?)?.replace("%player%", p.name) ?: ""
            )
        }
    }
}