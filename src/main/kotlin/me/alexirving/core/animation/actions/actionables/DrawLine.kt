/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * DrawLine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.loader.AniCompiler
import me.alexirving.core.exceptions.CompileError
import me.alexirving.core.utils.loc

class DrawLine(manager: EngineManager, args: Map<String, Any>, start: Int) : SuperAction(
    manager, args, start
) {

    override fun build(): MutableMap<Int, Action> {

        val a = mutableMapOf<Int, Action>()
        var current = start
        val frameCount = (args["frameCount"] as Int?) ?: 20
        val loc0 = (args["from"] as Map<String, Double>).loc()
        val loc1 = (args["to"] as Map<String, Double>).loc()

        val intX: Double = (loc0.x - loc1.x) / frameCount
        val intY: Double = (loc0.y - loc1.y) / frameCount
        val intZ: Double = (loc0.z - loc1.z) / frameCount

        var currentX = loc1.x
        var currentY = loc1.y
        var currentZ = loc1.z
        for (i in 0 until frameCount) {
            currentX += intX
            currentY += intY
            currentZ += intZ
            a[current++] = AniCompiler.compileAction(m, (args["action"] as Map<String, Any>).toMutableMap().apply {

                val loc = (this["location"] as Map<String, Double>).toMutableMap()
                loc["x"] = currentX
                loc["y"] = currentY
                loc["z"] = currentZ
                this["location"] = loc
            }) ?: throw CompileError("Compile error!")
        }

        return a
    }
}