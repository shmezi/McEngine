/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Loop.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.loader.AniCompiler

class Loop(manager: EngineManager, args: Map<String, Any>, start: Int) : SuperAction(
    manager, args, start
) {
    override fun build(): MutableMap<Int, Action> {

        val a = mutableMapOf<Int, Action>()

        for (i in start until ((args["amount"] as Int?) ?: 0)) {
            a[i] = AniCompiler.compileAction(m, args["action"] as Map<String, Any>)!!
        }


        return a
    }
}