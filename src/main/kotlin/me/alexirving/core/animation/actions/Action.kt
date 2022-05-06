/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Action.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.animation.actions

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import org.bukkit.Location

/**
 * Represents an action.
 */
abstract class Action(val m: EngineManager, val args: Map<String, Any>) {
    /**
     * Run when the action is executed
     */
    abstract fun run(session: AnimationSession, zeroPoint: Location, direction: Direction)
}