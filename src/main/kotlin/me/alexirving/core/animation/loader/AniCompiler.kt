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
import me.alexirving.core.EngineManager
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.objects.Frame
import org.bukkit.entity.EntityType
import org.reflections.Reflections
import java.io.File

object AniCompiler {
    private val reflections = Reflections("me.alexirving.core.animation.actions.actionables")
    private val actions: MutableSet<Class<out Action>> = reflections.getSubTypesOf(Action::class.java)
    private val superActions: MutableSet<Class<out SuperAction>> = reflections.getSubTypesOf(SuperAction::class.java)

    fun getActionClassFromName(name: String): Class<out Action> {
        return actions.filter { it.simpleName == name }[0]
    }

    fun doesActionExist(name: String?): Boolean {
        name ?: return false
        return !actions.none { it.simpleName == name }
    }

    fun getSuperActionClassFromName(name: String): Class<out SuperAction> {
        return superActions.filter { it.simpleName == name }[0]
    }

    fun doesSuperActionExist(name: String?): Boolean {
        name ?: return false
        return !superActions.none { it.simpleName == name }
    }

    fun compileAction(m: EngineManager, data: Map<String, Any>): Action? {
        return getActionClassFromName(data["type"] as String? ?: return null).getDeclaredConstructor(
            EngineManager::class.java,
            Map::class.java
        ).newInstance(m, data)
    }

    fun compileSuperAction(m: EngineManager, data: Map<String, Any>, start: Int): SuperAction? {

        return getSuperActionClassFromName(data["type"] as String? ?: return null).getDeclaredConstructor(
            EngineManager::class.java,
            Map::class.java,
            Int::class.java
        ).newInstance(m, data, start)
    }

    private val mapper = ObjectMapper()
    fun compileAnimation(m: EngineManager, json: File): Animation {
        val raw = mapper.readValue<RawAnimation>(json)
        val frames = mutableListOf<Frame>()


        raw.frames.map { actions ->
            frames.add(Frame(actions.filter { doesActionExist(it["type"] as String) }.map {
                compileAction(
                    m,
                    it
                )!!/*ye I know.. not what I want here either but it should still be null-safe*/
            }.toMutableList()))
        }
        for (sFrame in raw.frames.withIndex()) {
            for (action in sFrame.value)
                if (doesSuperActionExist(action["type"] as String))
                    for (a in compileSuperAction(m, action, sFrame.index)?.build() ?: mapOf())
                        if (frames.size > a.key) frames[a.key].actions.add(a.value)
                        else frames.add(Frame(mutableListOf(a.value)))
        }
        return Animation(
            raw.entities.mapValues { EntityType.valueOf(it.value) }.toMutableMap(),
            frames,
            raw.delay.toInt()
        )
    }

}