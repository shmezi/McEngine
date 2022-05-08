/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Frame.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.objects

import me.alexirving.core.animation.actions.Action
import me.alexirving.core.utils.Direction
import org.bukkit.Location

data class Frame(val actions: MutableList<Action>) {
    fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        for (action in actions.withIndex())
            action.value.run(session, zeroPoint, direction)
    }

}