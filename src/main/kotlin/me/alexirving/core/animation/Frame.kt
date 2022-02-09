package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.actions.Action
import org.bukkit.Location

class Frame(val actions: MutableList<Action>) {
    fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        for (action in actions.withIndex())
            action.value.run(session, zeroPoint, direction)
    }

}