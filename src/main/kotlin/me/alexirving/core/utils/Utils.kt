/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Utils.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.utils


import me.alexirving.core.McEngine
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.concurrent.Executors
import kotlin.random.Random

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
fun Double.nBZ() = if (this < 0) 0.0 else this
private val exe = Executors.newCachedThreadPool()

/**
 * Runs an async task without any delay and without using the bukkit internal threads utility.
 * @param task the task to execute.
 */
fun asyncNonBukkit(task: Runnable) {
    exe.submit(task)
}

fun Any?.print() = println(this)

fun Any?.printAsString() = println(this.toString())
fun Any?.printAsString(prefix: String) = println("$prefix${this.toString()}")
var c = 0
fun Any?.pq() = this.pq(null)
fun Any?.pqr() = pq(Random.nextInt(0, 100))
fun Any?.pq(number: Int) = this.pq("$number")
fun Any?.pq(prefix: String?) {

    val p = (prefix ?: "PRINTED").apply { replace(this[0], this[0].uppercaseChar()) }
    if (this == null) {
        println("[$p] null".color(Colors.RED))
        return
    }
    when (c) {
        0 -> println("[$p] $this".color(Colors.RED))
        1 -> println("[$p] $this".color(Colors.BLUE))
        2 -> println("[$p] $this".color(Colors.GREEN))
        3 -> println("[$p] $this".color(Colors.PURPLE))
        4 -> println("[$p] $this".color(Colors.CYAN))
        5 -> println("[$p] $this".color(Colors.YELLOW))
        else -> println("[$p] $this".color(Colors.CYAN))
    }

    c++
    if (c > 5)
        c = 0
}

fun ConfigurationSection.getLocation(path: String): Location? {
    return this.getLocation(path, Bukkit.getWorld(this.getString("$path.World") ?: "world") ?: return null)
}

fun ConfigurationSection.getLocation(path: String, world: World): Location {
    fun double(p: String) = this.getDouble("$path.$p")
    fun float(p: String) = double(p).toFloat()
    return Location(
        world,
        double("X"),
        double("Y"),
        double("Z"),
        float("Yaw"),
        float("Pitch")
    )
}