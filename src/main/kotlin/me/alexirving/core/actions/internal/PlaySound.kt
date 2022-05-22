/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * PlaySound.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.EngineManager
import me.alexirving.core.actions.Action
import me.alexirving.core.utils.Direction
import me.alexirving.core.utils.loc
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player

class PlaySound(manager: EngineManager, args: Map<String, Any>) : Action(manager, args) {
    override val id = "playSound"
    val offset = (args["offset"] as Map<String, Double>?).loc()

    override fun run(data: MutableMap<String, Any>) {
        (data["players"] as Set<Player>?)?.forEach {
            it.playSound(
                offset.getOffset(data["location"] as Location, data["direction"] as Direction),
                Sound.valueOf(args["sound"] as String),
                args["volume"] as Float,
                args["pitch"] as Float
            )
        }


    }


}