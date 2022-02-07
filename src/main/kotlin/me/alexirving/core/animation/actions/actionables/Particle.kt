package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.Offset
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.animation.validateLocation
import me.alexirving.core.items.ItemManager
import org.bukkit.Effect
import org.bukkit.Location

class Particle(pm: PacketManager, im: ItemManager, args: List<String>) : Action(pm, im, args) {
    var offset: Offset? = null

    init {
        val cords = validateLocation(args[0])
        if (cords != null)
            this.offset = Offset(cords[0], cords[1], cords[2])
        else
            println("ERROR, location is not correctly formatted! Given: \"${args[0]}\"")
    }


    override fun run(session: AnimationSession, zeroPoint: Location) {
        zeroPoint.world.playEffect(
            offset!!.getOffset(zeroPoint),
            Effect.valueOf(args[1]),
            args[2].toInt(),
            args[3].toInt()
        )
    }


}