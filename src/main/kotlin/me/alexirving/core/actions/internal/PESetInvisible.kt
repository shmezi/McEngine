/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SetInvisible.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.EngineManager
import me.alexirving.core.actions.AniAction
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.packets.PacketManager

class PESetInvisible(args: Map<String, Any>) : AniAction(args) {
    override val id = "SetInvisible"


    override fun run(session: AnimationSession, data: MutableMap<String, Any>) {
       PacketManager.setInvisible(session.standMap[args["entity"]]!!)
    }
}