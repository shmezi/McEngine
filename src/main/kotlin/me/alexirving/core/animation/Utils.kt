package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import org.bukkit.Location
import java.util.concurrent.Executors
import java.util.regex.Pattern

private val exe = Executors.newCachedThreadPool()

/**
 * Runs an async task without any delay and without using the bukkit internal threads utility.
 * @param task the task to execute.
 */
fun asyncNonBukkit(task: Runnable) {
    exe.submit(task)
}

fun Location.getFacing(): Facing {
    var rotation: Float = (yaw - 180) % 360
    if (rotation < 0) {
        rotation += 360.0f
    }
    return if (0 <= rotation && rotation < 22.5) {
        Facing.NORTH
    } else if (67.5 <= rotation && rotation < 112.5) {
        Facing.EAST
    } else if (157.5 <= rotation && rotation < 202.5) {
        Facing.SOUTH
    } else if (247.5 <= rotation && rotation < 292.5) {
        Facing.WEST
    } else {
        Facing.NORTH
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
    return this.replace(" ", "")
}

