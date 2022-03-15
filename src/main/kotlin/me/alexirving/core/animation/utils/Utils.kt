/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Utils.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.utils


import org.bukkit.Location
import java.util.regex.Pattern




fun Location.getFacing(): Direction {
    var rotation: Float = (yaw - 180) % 360
    if (rotation < 0) {
        rotation += 360.0f
    }
    return if (0 <= rotation && rotation < 22.5) {
        Direction.NORTH
    } else if (67.5 <= rotation && rotation < 112.5) {
        Direction.EAST
    } else if (157.5 <= rotation && rotation < 202.5) {
        Direction.SOUTH
    } else if (247.5 <= rotation && rotation < 292.5) {
        Direction.WEST
    } else {
        Direction.NORTH
    }

}


private val matchable = Pattern.compile("\\[(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)]")

fun validateLocation(eval: String): List<String>? {
    val matcher = matchable.matcher(eval)
    val a = mutableListOf<String>()
    return if (matcher.matches()) {
        a.apply {
            add(matcher.group(1))
            add(matcher.group(3))
            add(matcher.group(5))
        }
        a
    } else
        null
}

fun toLocation(eval: String): List<Double>? {
    val matcher = matchable.matcher(eval)
    val a = mutableListOf<Double>()
    return if (matcher.matches()) {
        a.apply {
            add(matcher.group(1).toDouble())
            add(matcher.group(3).toDouble())
            add(matcher.group(5).toDouble())
        }
        a
    } else
        null
}

fun String.space(): String {
    return replace(" ", "")
}
