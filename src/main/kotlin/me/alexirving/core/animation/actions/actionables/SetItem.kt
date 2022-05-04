/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SetItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.exceptions.NotFoundException
import org.bukkit.Location

class SetItem(manager: EngineManager, args: List<String>) : Action(manager, args) {

    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
        m.packet.setItem(
            session.standMap[args[0]]!!,
            args[1].toInt(),
            m.item.bases[args[2]]?.buildSimple() ?: throw NotFoundException("Item \"${args[2]}\" was not found!")
        )

    }
}