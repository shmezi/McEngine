/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SetDisplayName.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.actions.internal

import me.alexirving.core.actions.animations.AniAction
import me.alexirving.core.actions.animations.AniData
import me.alexirving.core.packets.PacketManager

class PESetDisplayName(args: AniData) : AniAction(args) {
    override val id = "SetDisplayName"


    override fun run(data: AniData) {

        PacketManager.setDisplayName(
            data.getstandMap()[args["entity"]?.asString()] ?: return,
            args["name"]?.asString() ?: return
        )
    }
}