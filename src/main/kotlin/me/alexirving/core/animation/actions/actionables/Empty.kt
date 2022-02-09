package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.Direction
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.items.ItemManager
import org.bukkit.Location

class Empty(pm: PacketManager, im: ItemManager, args: List<String>) : Action(pm, im, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
    }
}