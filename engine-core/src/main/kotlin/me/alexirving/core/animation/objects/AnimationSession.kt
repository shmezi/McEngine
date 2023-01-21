/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationSession.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.objects

import me.alexirving.core.McEngine
import me.alexirving.core.actions.data.ActionData
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.utils.Direction
import me.alexirving.core.utils.pq
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * An animation session represents a running session of an [Animation] playing for a list of [Player]s.
 * @param engine The plugin that this session belongs to.
 * @param viewers The players that this session belongs to.
 * @param location The [Location] that this session is playing at.
 * @param animation The [Animation] that this session is playing.
 */
data class AnimationSession(
    private val engine: McEngine,
    val viewers: MutableSet<Player>,
    private val location: Location,
    val animation: Animation,
    val direction: Direction
) {
    val standMap: MutableMap<String, Int> = mutableMapOf() //Map of stand name to stand id in the packetMap
    private val scheduler = engine.server.scheduler

    /**
     * Starts the animation session.
     */
    fun start(done: () -> Unit) {
        "whatever".pq()
        val yaw = when (direction) {
            Direction.NORTH -> 0f
            Direction.EAST -> 90f
            Direction.SOUTH -> 180f
            Direction.WEST -> 270f
        }
        val loc = location.clone()
        loc.yaw = yaw
        for (name in animation.entities) {
            standMap[name.key] = PacketManager.spawn(name.value, viewers, loc)
        }
        var timeStamp = 0
        val data = ActionData().set("session", this).set("players", viewers)
        for (frame in animation.sequence) {
            scheduler.runTaskLaterAsynchronously(engine, Runnable {
                frame.run(data)
            }, timeStamp.toLong())
            timeStamp += animation.frameDelay
        }

        scheduler.runTaskLaterAsynchronously(engine, Runnable { stop(); done() }, timeStamp.toLong())
    }

    /**
     * Starts the animation session.
     */
    fun start() {
        start {}
    }


    private fun stop() {
        for (armor in animation.entities.keys)
            PacketManager.kill(standMap[armor]!!)
    }

}

