/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.commands.AnimationCMD
import me.alexirving.core.commands.Tool
import me.alexirving.core.legacyItems.ItemManager
import me.alexirving.core.utils.copyOver
import org.bstats.bukkit.Metrics
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import  me.alexirving.core.item.ItemManager as Im

class McEngine : JavaPlugin() {

    val im = ItemManager()
    lateinit var am: AnimationManager
    override fun onEnable() {
        McEngineAPI.instance = this
        saveDefaultConfig()
        Im.reload(File(dataFolder, "items"))
        Metrics(this, 14580)
        copyOver(dataFolder, "items.yml", "animations", "items", "animations/Default.yml", "items/SuperPick.json")
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        this.am = AnimationManager(File(dataFolder, "animations"), this)
        getCommand("tool").executor = Tool()
        getCommand("animation").executor = AnimationCMD(this)
    }

    fun reload() {
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        am.reload()
        reloadConfig()
    }

}