/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Particle.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.Action
import me.alexirving.core.actions.data.ActionData
import me.alexirving.core.utils.loc

class Particle(args: ActionData) : Action(args) {
    override val id = "Particle"
    private val offset = (args["offset"]?.asType<Map<String, Double>>()).loc()


    override fun run(data: ActionData) {
        data["players"]?.asPlayers()?.forEach {
            it.playEffect(
                offset.getOffset(
                    data["location"]?.asLocation() ?: return,
                    data["direction"]?.asDirection() ?: return
                ),
                args["effect"]?.asEffect() ?: return,
                args["data"]?.asByte()
            )

        }


    }


}