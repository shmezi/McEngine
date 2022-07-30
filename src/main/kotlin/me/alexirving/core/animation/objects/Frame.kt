/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Frame.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.objects

import me.alexirving.core.actions.Action

/**
 * Represents an animation frame in an [Animation], please be aware that stuff like commands are run per player in the [AnimationSession]
 * @param actions The actions to be run when the frame is run
 * */
data class Frame(val actions: MutableList<Action>) {
    fun run(data:MutableMap<String,Any>) {
        for (action in actions.withIndex())
            action.value.run(data)
    }

}