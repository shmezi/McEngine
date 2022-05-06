/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Particle.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.objects.Offset
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.utils.loc
import org.bukkit.Effect
import org.bukkit.Location

class Particle(manager: EngineManager, args: Map<String, Any>) : Action(manager, args) {
    var offset: Offset? = null

    init {
        this.offset = (args["location"] as Map<String, Double>).loc()
    }


    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        zeroPoint.world?.playEffect(
            offset!!.getOffset(zeroPoint, direction),
            Effect.valueOf(args["effect"] as String),
            args["data"] as Int,
            args["radius"] as Int
        )
    }


}