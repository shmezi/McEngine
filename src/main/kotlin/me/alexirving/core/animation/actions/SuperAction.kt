package me.alexirving.core.animation.actions
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine

/**
 * Represents a super action.
 * A super action is a function that generates other actions. (Example: )
 */
abstract class SuperAction(
    val pl: McEngine,
    val raw: String,
    val start: Int
) {
    /**
     * Run when the action is executed
     */
    abstract fun build(): MutableMap<Int, Action>
}