/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tp.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.EngineManager
import me.alexirving.core.actions.animations.AniAction
import me.alexirving.core.actions.animations.AniData
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.objects.Offset
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.utils.Direction
import me.alexirving.core.utils.loc
import org.bukkit.Location

class Tp(manager: EngineManager, args: AniData) : AniAction( args) {
    override val id = "Tp"
    var offset: Offset? = null

    init {
        this.offset = ((args["location"] as Map<String, Double>)).loc()
    }


    override fun run(data: AniData) {
        val session = data.getSession()
        val t = session.standMap[args["entity"]?.asString()] //Lol, note to self im kinda dumb used entity as key before lols! | Lol.. me from other past umm isnt this funny writing to urself and stuff
        if (t == null) {
            println("ERROR, stand \"${args["entity"]}\" was not found!")
            return
        }
        val yaw = when (data.getSession().direction) {
            Direction.NORTH -> 0f
            Direction.EAST -> 90f
            Direction.SOUTH -> 180f
            Direction.WEST -> 270f
        }
        val loc = (data["location"] as Location).clone()
        loc.yaw = yaw
        PacketManager.tp(t, offset!!.getOffset(loc, session.direction).add(0.5, -1.2, 0.5))

    }


}