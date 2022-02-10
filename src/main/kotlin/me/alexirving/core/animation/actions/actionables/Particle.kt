package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.Direction
import me.alexirving.core.animation.Offset
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.validateLocation
import org.bukkit.Effect
import org.bukkit.Location

class Particle(pl: McEngine, args: List<String>) : Action(pl, args) {
    var offset: Offset? = null

    init {
        val cords = validateLocation(args[0])
        if (cords != null)
            this.offset = Offset(cords[0], cords[1], cords[2])
        else
            println("ERROR, location is not correctly formatted! Given: \"${args[0]}\"")
    }


    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        zeroPoint.world.playEffect(
            offset!!.getOffset(zeroPoint, direction),
            Effect.valueOf(args[1]),
            args[2].toInt(),
            args[3].toInt()
        )
    }


}