package me.alexirving.core.utils
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */

import me.alexirving.core.McEngine
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.concurrent.Executors


fun registerListeners(plugin: JavaPlugin, vararg listeners: Listener) {
    for (l in listeners)
        plugin.server.pluginManager.registerEvents(l, plugin)
}

fun format(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}

fun copyOver(dataFolder: File, vararg fileNames: String) {
    for (name in fileNames) {
        val tc = File(dataFolder, name)
        if (tc.exists())
            continue
        if (name.matches(".+\\..+\$".toRegex()))
            Files.copy(
                McEngine::class.java.classLoader.getResourceAsStream(name),
                tc.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        else
            tc.mkdir()

    }
}

/**
 * Guarantees a number above 0
 */
fun Int.nBZ() = if (this < 0) 0 else this

private val exe = Executors.newCachedThreadPool()

/**
 * Runs an async task without any delay and without using the bukkit internal threads utility.
 * @param task the task to execute.
 */
fun asyncNonBukkit(task: Runnable?) {
    exe.submit(task)
}