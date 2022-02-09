package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * An animation session represents a session of an [Animation] playing for a [Player].
 * @param plugin The plugin that this session belongs to.
 * @param viewers The players that this session belongs to.
 * @param location The [Location] that this session is playing at.
 * @param animation The [Animation] that this session is playing.
 */
class AnimationSession(
    private val plugin: McEngine,
    private val viewers: MutableList<Player>,
    private val location: Location,
    val animation: Animation,
    val direction: Direction
) {
    val standMap: MutableMap<String, Int> = mutableMapOf() //Map of stand name to stand id in the packetMap
    private val scheduler = plugin.server.scheduler
    val pm = plugin.am.pm

    /**
     * Starts the animation session.
     */
    fun start(done: () -> Unit) {
        for (name in animation.standNames)
            standMap[name.key] = pm.spawn(name.value, viewers, location)
        var timeStamp = 0
        for (frame in animation.sequence) {
            scheduler.runTaskLaterAsynchronously(plugin, {
                frame.run(this, location, direction)
            }, timeStamp.toLong())
            timeStamp += animation.frameDelay
        }
        scheduler.runTaskLaterAsynchronously(plugin, { stop(); done() }, timeStamp.toLong())
    }

    /**
     * Starts the animation session.
     */
    fun start() {
        start { }
    }

    private fun stop() {
        for (armor in animation.standNames.keys)
            pm.kill(standMap[armor]!!)
    }

}

