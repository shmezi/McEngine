package me.alexirving.core.animation.objects
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.utils.Direction
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * An animation session represents a session of an [Animation] playing for a [Player].
 * @param pl The plugin that this session belongs to.
 * @param viewers The players that this session belongs to.
 * @param location The [Location] that this session is playing at.
 * @param animation The [Animation] that this session is playing.
 */
class AnimationSession(
    private val pl: McEngine,
    private val viewers: MutableList<Player>,
    private val location: Location,
    val animation: Animation,
    val direction: Direction
) {
    val standMap: MutableMap<String, Int> = mutableMapOf() //Map of stand name to stand id in the packetMap
    private val scheduler = pl.server.scheduler
    val pm = pl.am.pm

    /**
     * Starts the animation session.
     */
    fun start(done: () -> Unit) {
        val yaw = when (direction) {
            Direction.NORTH -> 0f
            Direction.EAST -> 90f
            Direction.SOUTH -> 180f
            Direction.WEST -> 270f
        }
        val loc = location.clone()
        loc.yaw = yaw
        for (name in animation.standNames)
            standMap[name.key] = pm.spawn(name.value, viewers, loc)
        var timeStamp = 0
        for (frame in animation.sequence) {
            scheduler.runTaskLaterAsynchronously(pl, Runnable {
                frame.run(this, location, direction)
            }, timeStamp.toLong())
            timeStamp += animation.frameDelay
        }
        scheduler.runTaskLaterAsynchronously(pl, Runnable { stop(); done() }, timeStamp.toLong())
    }

    /**
     * Starts the animation session.
     */
    fun start() {
        start {}
    }

    private fun stop() {
        for (armor in animation.standNames.keys)
            pm.kill(standMap[armor]!!)
    }

}

