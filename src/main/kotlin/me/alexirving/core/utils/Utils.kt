/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Utils.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.utils


import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.Offset
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
fun Int?.nBZ() = if ((this ?: 0) < 0) 0 else this ?: 0
fun Double?.nBZ() = if ((this ?: 0.0) < 0.0) 0.0 else this ?: 0.0

fun Int.nB(value: Int) = if (this < value) value else this
fun Double.nB(value: Double) = if (this < value) value else this
private val exe = Executors.newCachedThreadPool()

/**
 * Runs an async task without any delay and without using the bukkit internal threads utility.
 * @param task the task to execute.
 */
fun asyncNonBukkit(task: Runnable) {
    exe.submit(task)
}

fun <T> T?.print(): T? {
    println(this)
    return this
}


var c = 0
fun <T> T?.pq(): T? {
    this.pq(null)
    return this
}

fun <T> T?.pqr(): T? {
    pq(Random.nextInt(0, 100))
    return this
}

fun <T> T?.pq(number: Int): T? {
    this.pq("$number")
    return this
}

fun <T> T?.pq(prefix: String?): T? {

    val p = (prefix ?: "PRINTED").apply { replace(this[0], this[0].uppercaseChar()) }
    if (this == null) {
        println("[$p] null".color(Colors.RED))
        return null
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
    return this
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

fun Location.getFacing(): Direction {
    var rotation: Float = (yaw - 180) % 360
    if (rotation < 0) {
        rotation += 360.0f
    }
    return if (0 <= rotation && rotation < 22.5) {
        Direction.NORTH
    } else if (67.5 <= rotation && rotation < 112.5) {
        Direction.EAST
    } else if (157.5 <= rotation && rotation < 202.5) {
        Direction.SOUTH
    } else if (247.5 <= rotation && rotation < 292.5) {
        Direction.WEST
    } else {
        Direction.NORTH
    }

}

enum class RomanNumerals(val amount: Int) {
    M(1000),
    CM(900),
    D(500),
    CD(400),
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1)
}

fun Int.toRoman(): String {
    var current = this
    val appendable = StringBuilder()
    for (r in RomanNumerals.values()) {
        val remove = current.floorDiv(r.amount)
        appendable.append(r.name.repeat(remove))
        current -= (r.amount * remove)
        if (current <= 0)
            return appendable.toString()
    }
    return appendable.toString()
}

fun Map<String, Double>?.loc() =
    if (this == null) Offset(0.0, 0.0, 0.0) else Offset(this["x"] ?: 0.0, this["y"] ?: 0.0, this["z"] ?: 0.0)

/**
 * Replaces a map of placeholders from a string.
 */
fun String.replacePH(map: Map<String, String>): String {
    var replaceMe = this
    map.forEach {
        replaceMe = replaceMe.replace(it.key, it.value)
    }
    return replaceMe
}