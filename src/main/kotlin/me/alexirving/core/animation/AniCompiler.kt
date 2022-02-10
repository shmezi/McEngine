package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.exceptions.CompileError
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.items.ItemManager
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.EntityType
import org.reflections.Reflections
import java.util.regex.Pattern

object AniCompiler {
    private val ac: Pattern = Pattern.compile("(.+)\\((.*)\\)\$")
    private val sav: Pattern =
        Pattern.compile("(.+)\\(.+\\(.*\\).*\\)$")

    private val reflections = Reflections("me.alexirving.core.animation.actions.actionables")
    private val actions: MutableSet<Class<out Action>> = reflections.getSubTypesOf(Action::class.java)
    private val sActions: MutableSet<Class<out SuperAction>> = reflections.getSubTypesOf(SuperAction::class.java)

    fun getActionClassFromName(name: String): Class<out Action> {
        return actions.filter { it.simpleName == name }[0]
    }

    fun doesActionExist(name: String): Boolean {
        return !actions.none { it.simpleName == name }
    }

    fun getSuperActionClassFromName(name: String): Class<out SuperAction> {
        return sActions.filter { it.simpleName == name }[0]
    }

    fun doesSuperActionExist(name: String): Boolean {
        return !sActions.none { it.simpleName == name }
    }

    fun compileAction(pl: McEngine, toCompile: String): Action {
        val rawAction = ac.matcher(toCompile)
        if (!rawAction.matches()) throw CompileError("Improper written action: \"$toCompile\"")
        if (!doesActionExist(rawAction.group(1))) throw CompileError("Function does not exist:\"${rawAction.group(1)}\" in: \"$toCompile\"")

        return getActionClassFromName(rawAction.group(1)).getDeclaredConstructor(
            McEngine::class.java,
            List::class.java
        ).newInstance(pl, rawAction.group(2).split(";"))
    }

    fun compileSuperAction(pl: McEngine, toCompile: String, start: Int): SuperAction {
        val rawAction = sav.matcher(toCompile)
        if (!rawAction.matches()) throw CompileError("Improper written action: \"$toCompile\"")
        if (!doesSuperActionExist(rawAction.group(1))) throw CompileError("Function does not exist:\"${rawAction.group(1)}\" in: \"$toCompile\"")
        return getSuperActionClassFromName(rawAction.group(1)).getDeclaredConstructor(
            McEngine::class.java,
            String::class.java,
            Int::class.java
        ).newInstance(pl, toCompile, start)
    }


    fun compileAnimation(pl: McEngine, aniFile: FileConfiguration): Animation {
        val names = mutableMapOf<String, EntityType>()
        for (set in aniFile.getConfigurationSection("Stands").getKeys(false)) {
            names[set] = EntityType.valueOf(aniFile.getString("Stands.$set").uppercase())
        }

        val frames = mutableListOf<Frame>()
        val toBeDone = mutableMapOf<Int, MutableList<String>>()
        //First pass of adding frames (Normal actions)
        for (rawFrame in aniFile.getStringList("Frames").withIndex()) {
            val currentFrame = mutableListOf<Action>()
            for (rawAction in rawFrame.value.split("|")) {
                if (sav.matcher(rawAction.space()).matches()) {
                    if (toBeDone.containsKey(rawFrame.index)) {
                        toBeDone[rawFrame.index]!!.add(rawAction)
                    } else {
                        toBeDone[rawFrame.index] = mutableListOf(rawAction)
                    }
                } else
                    currentFrame.add(compileAction(pl, rawAction))
            }
            frames.add(Frame(currentFrame))
        }
        //Second pass of adding frames (Super actions)
        for (superActions in toBeDone) { //Starting frame | List of raw super actions
            for (rawSuper in superActions.value) {
                val comp = compileSuperAction(pl, rawSuper, superActions.key)
                for (action in comp.build()) {
                    if (frames.size > action.key)
                        frames[action.key].actions.add(action.value)
                    else
                        frames.add(Frame(mutableListOf(action.value)))


                }
            }
        }
        return Animation(names, frames, aniFile.getInt("FrameDelay"))
    }

}