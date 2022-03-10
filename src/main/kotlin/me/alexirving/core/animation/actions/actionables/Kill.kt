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
import org.bukkit.Location

class Kill(pl: McEngine, args: List<String>) : Action(pl, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        session.pm.kill(session.standMap[args[0]]!!)
    }


}