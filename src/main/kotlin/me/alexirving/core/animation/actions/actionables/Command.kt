package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.animation.actions.Action
import org.bukkit.Bukkit
import org.bukkit.Location

class Command(pl: McEngine, args: List<String>) : Action(pl, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        if (!Bukkit.isPrimaryThread())
            Bukkit.getScheduler().runTask(pl) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0])
            }
        else
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0])
    }
}