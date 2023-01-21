/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * PlaySound.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.Action
import me.alexirving.core.actions.animations.AniData
import me.alexirving.core.actions.data.ActionData
import me.alexirving.core.utils.loc
import org.bukkit.Sound

class PlaySound(args: AniData) : Action(args) {
    override val id = "playSound"
    private val offset = (args["offset"]?.asType<Map<String, Double>>()).loc()

    override fun run(data: ActionData) {
        data["players"]?.asPlayers()?.forEach {
            it.playSound(
                offset.getOffset(
                    data["location"]?.asLocation() ?: return,
                    data["direction"]?.asDirection() ?: return
                ),
                Sound.valueOf(args["sound"]?.asString()?:return),
                args["volume"]?.asFloat()?:return,
                args["pitch"]?.asFloat()?:return
            )
        }


    }


}