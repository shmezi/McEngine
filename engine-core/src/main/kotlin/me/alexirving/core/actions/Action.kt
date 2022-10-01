/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Action.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.actions

import me.alexirving.core.animation.objects.Offset

/**
 * Represents an action.
 * @param args The arguments to build this action, such as an [AnimationSession] or an offset [Offset]
 */
abstract class Action(val args: Map<String, Any>) {
    abstract val id: String

    /**
     * Run when the action is executed
     * @param data The data that should be used to execure the action such as a [Player] or a [Location].
     *
     */
    abstract fun run(data: MutableMap<String, Any> = mutableMapOf())
}