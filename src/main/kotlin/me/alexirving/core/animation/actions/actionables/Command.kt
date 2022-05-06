/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Command.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import org.bukkit.Bukkit
import org.bukkit.Location

class Command(manager: EngineManager, args: Map<String, String>) : Action(manager, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        if (!Bukkit.isPrimaryThread())
            Bukkit.getScheduler().runTask(m.engine, Runnable {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args["command"] as String? ?: "")
            })
        else
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args["command"] as String? ?: "")
    }
}