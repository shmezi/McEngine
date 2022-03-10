package me.alexirving.core.animation.actions
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import org.bukkit.Location

/**
 * Represents an action.
 */
abstract class Action(val pl: McEngine, val args: List<String>) {
    /**
     * Run when the action is executed
     */
    abstract fun run(session: AnimationSession, zeroPoint: Location, direction: Direction)
}