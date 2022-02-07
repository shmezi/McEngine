package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import org.bukkit.entity.EntityType


/**
 * An animation that can be played with an [AnimationSession].
 * @param frameDelay The delay between each frame in milliseconds.
 * @param sequence The frames of the animation.
 * @param standNames The names of the armor stands to be used in the animation.
 */
class Animation(val standNames: MutableMap<String, EntityType>, val sequence: MutableList<Frame>, val frameDelay: Int)