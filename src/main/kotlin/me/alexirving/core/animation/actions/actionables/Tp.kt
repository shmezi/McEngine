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
import me.alexirving.core.animation.toLocation
import org.bukkit.Location

class Tp(pl: McEngine, args: List<String>) : Action(pl, args) {

    var offset: Offset? = null

    init {
        val cords = toLocation(args[1])

        if (cords != null) {
            this.offset = Offset(cords[0], cords[1], cords[2])
        } else
            println("Error while compiling Tp, location is not correctly formatted! Given: \"$args\"")
    }


    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        val t = session.standMap[args[0]]
        if (t == null) {
            println("ERROR, stand \"${args[0]}\" was not found!")
            return
        }
        val yaw = when (direction) {
            Direction.NORTH -> 0f
            Direction.EAST -> 90f
            Direction.SOUTH -> 180f
            Direction.WEST -> 270f
        }
        val loc = zeroPoint.clone()
        loc.yaw = yaw
        session.pm.tp(t, offset!!.getOffset(loc, direction).add(0.5, -1.2, 0.5))

    }


}