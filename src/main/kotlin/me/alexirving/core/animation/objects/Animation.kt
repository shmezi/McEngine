/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Animation.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.objects

import org.bukkit.entity.EntityType


/**
 * An animation that can be played with an [AnimationSession].
 * @param frameDelay The delay between each frame in milliseconds.
 * @param sequence The frames of the animation.
 * @param entities The names and types for the entities to be used in the [AnimationSession]
 *
 */
data class Animation(
    val entities: MutableMap<String, EntityType>,
    val sequence: MutableList<Frame>,
    val frameDelay: Int
)