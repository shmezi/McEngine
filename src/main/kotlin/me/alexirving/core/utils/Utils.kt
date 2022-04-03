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
var c = true
fun Any?.pq() {
    if (this == null) {
        println("null".color(Colors.RED))
        return
    }
    c = if (c) {
        println("PRINTED VALUE: ${this.toString()}".color(Colors.BG_RED))
        false
    } else {
        println("PRINTED VALUE: ${this.toString()}".color(Colors.BG_BLUE))
        true
    }
}