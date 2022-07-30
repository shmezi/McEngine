/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SetItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.AniAction
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.item.ItemManager
import me.alexirving.core.packets.PacketManager

class PESetSlot(args: Map<String, Any>) : AniAction(args) {
    override val id = "SetItem"

    override fun run(session: AnimationSession, data: MutableMap<String, Any>) {
        PacketManager.setItem(
            session.standMap[args["entity"]]!!,
            args["slot"] as Int,
            ItemManager.bases[args["itemId"]]?.buildSimple()
                ?: throw NotFoundException("Item \"${args["itemId"]}\" was not found!")
        )
    }
}