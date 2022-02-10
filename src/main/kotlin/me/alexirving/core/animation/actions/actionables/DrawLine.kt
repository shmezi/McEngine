package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.McEngine
import me.alexirving.core.animation.AniCompiler
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.animation.toLocation
import me.alexirving.core.items.ItemManager
import java.util.regex.Pattern

class DrawLine(pl:McEngine, rawStatement: String, start: Int) : SuperAction(
    pl, rawStatement, start
) {
    private val pattern: Pattern =
        Pattern.compile("(.+)\\((((.+)\\((.*(\\[.+]))\\));(\\d+);(\\[-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?]);(\\[-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?]))\\)\$")


    override fun build(): MutableMap<Int, Action> {
        val args = pattern.matcher(raw)
        if (!args.matches()) {
            println("Error: Invalid DrawLine statement \"$raw\"")
            return mutableMapOf()
        }
        val a = mutableMapOf<Int, Action>()
        var current = start
        val frameCount = args.group(7).toInt()
        val loc0 = toLocation(args.group(12))!!
        val loc1 = toLocation(args.group(8))!!

        val intX: Double = (loc0[0] - loc1[0]) / frameCount
        val intY: Double = (loc0[1] - loc1[1]) / frameCount
        val intZ: Double = (loc0[2] - loc1[2]) / frameCount

        var currentX = loc1[0]
        var currentY = loc1[1]
        var currentZ = loc1[2]
        for (i in 0 until frameCount) {
            currentX += intX
            currentY += intY
            currentZ += intZ
            a[current++] = AniCompiler.compileAction(pl, args.group(3)
                .replace("X",currentX.toString())
                .replace("Y",currentY.toString())
                .replace("Z",currentZ.toString()))
        }

        return a
    }
}