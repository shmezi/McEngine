/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SuperAction.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions

import me.alexirving.core.EngineManager
import me.alexirving.core.McEngine

/**
 * Represents a super action.
 * A super action is a function that generates other actions. (Example: [DrawCircle])
 */
abstract class SuperAction(
    val m: EngineManager,
    val raw: String,
    val start: Int
) {
    /**
     * Run when the action is executed
     */
    abstract fun build(): MutableMap<Int, Action>
}