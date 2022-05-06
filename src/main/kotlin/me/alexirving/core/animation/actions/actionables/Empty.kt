/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Empty.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.actions.actionables

import me.alexirving.core.EngineManager
import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.animation.actions.Action
import org.bukkit.Location

class Empty(manager: EngineManager, args: Map<String, Any>) : Action(manager, args) {
    override fun run(session: AnimationSession, zeroPoint: Location, direction: Direction) {
    }
}