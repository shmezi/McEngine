package me.alexirving.core.animation.actions
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.items.ItemManager
import org.bukkit.Location

/**
 * Represents an action.
 */
abstract class Action(val pm: PacketManager, val im: ItemManager, val args: List<String>) {
    /**
     * Run when the action is executed
     */
    abstract fun run(session: AnimationSession, zeroPoint: Location)
}