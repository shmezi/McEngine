/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Particle.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.animation.objects.Offset
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.utils.validateLocation
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
        zeroPoint.world?.playEffect(
            offset!!.getOffset(zeroPoint, direction),
            Effect.valueOf(args[1]),
            args[2].toInt(),
            args[3].toInt()
        )
    }


}