/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.animation

import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.filters.FileExtensionFilter
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
            println("Animation folder is not a directory!")
        loadedAnimations.clear()
        for (file in aniFolder.listFiles(FileExtensionFilter("yml")) ?: return) {
            val a = YamlConfiguration().apply { load(file) }
            val name = file.nameWithoutExtension
            loadedAnimations[name] = AniCompiler.compileAnimation(pl, a)
            println("Loaded animation: \"$name\".".color(Colors.PURPLE))
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