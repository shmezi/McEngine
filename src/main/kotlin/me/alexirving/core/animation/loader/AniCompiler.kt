/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AniCompiler.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.loader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.alexirving.core.actions.ActionManager
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.objects.Frame
import org.bukkit.entity.EntityType
import java.io.File

object AniCompiler {

    private val mapper = ObjectMapper()
    fun compileAnimation(json: File): Animation {
        val rawAnimation = mapper.readValue<RawAnimation>(json)
        val frames = mutableListOf<Frame>()

        //Looping through the raw frames
        rawAnimation.frames.forEach { rawActionList ->

            frames.add(Frame(rawActionList.filter {
                //Validating the action exists
                ActionManager.doesActionExist(it["type"] as String)

                //Transforming the raw actions to a list of Action instances
            }.map {

                ActionManager.compileAction(
                    it
                )!!/*ye I know.. not what I want here either but it should still be null-safe*/

            }.toMutableList()))
        }
        for (sFrame in rawAnimation.frames.withIndex()) {
            for (a in sFrame.value) if (ActionManager.doesSuperActionExist(a["type"] as String)) for (b in ActionManager.compileSuperAction(
                a, sFrame.index
            )?.build() ?: mapOf()) if (frames.size > b.key) frames[b.key].actions.add(b.value)
            else frames.add(Frame(mutableListOf(b.value)))
        }
        return Animation(
            rawAnimation.entities.mapValues { EntityType.valueOf(it.value) }.toMutableMap(),
            frames,
            rawAnimation.delay
        )
    }

}