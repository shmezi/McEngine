package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.exceptions.NotADirectory
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.utils.Colors
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class AnimationManager(private val aniFolder: File, private val pl: McEngine) {
    private val loadedAnimations = HashMap<String, Animation>()
    val pm = PacketManager()


    init {
        reload()
    }

    fun reload() {
        if (!aniFolder.isDirectory)
            throw NotADirectory("Animation folder provided is not a directory!")
        loadedAnimations.clear()
        for (file in aniFolder.listFiles())
            if (file.extension == "yml") {
                val a = YamlConfiguration()
                a.load(file)

                val ani = AniCompiler.compileAnimation(pl, a)
                val name = file.nameWithoutExtension
                loadedAnimations[name] = ani
                println("${Colors.BG_PURPLE}${Colors.BLUE}Loaded animation: $name!")
            }
    }

    fun getAnimation(name: String): Animation? {
        if (loadedAnimations[name] == null)
            throw NullPointerException("Animation \"$name\" Not found!")
        return loadedAnimations[name]
    }

    fun getAnimationNames(): Set<String> {
        return loadedAnimations.keys
    }

}