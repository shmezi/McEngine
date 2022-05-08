/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SetDisplayName.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.utils.Direction
import org.bukkit.Location
import kotlin.experimental.and

class SetDisplayName(manager: EngineManager, args: Map<String, Any>) : Action(manager, args) {


    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        m.packet.setDisplayName(session.standMap[args["entity"]]!!, args["name"] as String)
        var s: Byte = 0x00;
        if (true)
            s and s
    }
}