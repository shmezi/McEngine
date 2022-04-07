/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Utils.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.utils


import de.tr7zw.changeme.nbtapi.NBTItem
import dev.triumphteam.gui.builder.item.ItemBuilder
import me.alexirving.core.McEngine
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.concurrent.Executors
import kotlin.random.Random

val mm = MiniMessage.miniMessage()


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


fun getPickaxe(config: FileConfiguration, player: Player): ItemStack {
    val b = ItemBuilder.from(Material.valueOf(config.getString("Pickaxe.Material").uppercase()))

    var lore = config.getStringList("Pickaxe.Lore").toMutableList()
    var name = config.getString("Pickaxe.Name")
    val replaceable = mutableMapOf<String, String>()
    replaceable["mined"] = "0"
    replaceable["level"] = "1"
    replaceable["player"] = player.displayName
    replaceable["uuid"] = player.uniqueId.toString()
    replaceable.forEach { replacer ->
        name = name.replace("%${replacer.key}%", replacer.value)
        lore = lore.map { it.replace("%${replacer.key}%", replacer.value) }.toMutableList()
    }
    b.name(mm.deserialize(name))
    b.lore(lore.map { mm.deserialize(it) })
    val nbt = NBTItem(b.build())
    nbt.setObject("enchants", mutableMapOf<String, String>())
    nbt.setLong("mined", 0)
    nbt.setInteger("level", 0)
    return nbt.item
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
fun asyncNonBukkit(task: Runnable?) {
    exe.submit(task)
}

fun Any?.print() = println(this)

fun Any?.printAsString() = println(this.toString())
fun Any?.printAsString(prefix: String) = println("$prefix${this.toString()}")
var c = 0
fun Any?.pq() = this.pq(null)
fun Any?.pqr() = pq(Random.nextInt(0, 100))
fun Any?.pq(number: Int) = this.pq("[$number] ")
fun Any?.pq(prefix: String?) {
    if (this == null) {
        println("null".color(Colors.RED))
        return
    }
    val p = prefix ?: "PRINTED VALUE: "
    when (c) {
        0 -> println("$p$this".color(Colors.RED))
        1 -> println("$p$this".color(Colors.BLUE))
        2 -> println("$p$this".color(Colors.GREEN))
        3 -> println("$p$this".color(Colors.PURPLE))
        4 -> println("$p$this".color(Colors.CYAN))
        5 -> println("$p$this".color(Colors.YELLOW))
    }
    c++
    if (c > 5)
        c = 0
}