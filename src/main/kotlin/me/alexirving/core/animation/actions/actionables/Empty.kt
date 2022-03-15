/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Empty.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.animation.actions.Action
import org.bukkit.Location

class Empty(pl:McEngine, args: List<String>) : Action(pl, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
    }
}