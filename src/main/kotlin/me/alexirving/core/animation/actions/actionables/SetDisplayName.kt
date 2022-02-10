package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.Direction
import me.alexirving.core.animation.actions.Action
import org.bukkit.Location

class SetDisplayName(pl: McEngine, args: List<String>) : Action(pl, args) {


    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        pl.am.pm.setDisplayName(session.standMap[args[0]]!!, args[1])
    }
}