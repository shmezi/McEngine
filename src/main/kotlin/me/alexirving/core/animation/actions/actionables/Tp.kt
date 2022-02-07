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
import me.alexirving.core.animation.toLocation
import me.alexirving.core.items.ItemManager
import org.bukkit.Location

class Tp(pm: PacketManager, im: ItemManager, args: List<String>) : Action(pm, im, args) {

    var offset: Offset? = null

    init {
        val cords = toLocation(args[1])

        if (cords != null) {
            this.offset = Offset(cords[0], cords[1], cords[2])
        } else
            println("ERROR, location is not correctly formatted! Given: \"${args[0]}\"")
    }


    override fun run(session: AnimationSession, zeroPoint: Location) {
        session.pm.tp(session.standMap[args[0]]!!, offset!!.getOffset(zeroPoint).add(0.5, -1.2, 0.5))

    }


}