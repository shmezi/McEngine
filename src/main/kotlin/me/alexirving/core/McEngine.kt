package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
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
        Metrics(this, 14580)
        copyOver(dataFolder, "items.yml", "animations", "items", "animations/Default.yml", "items/SuperPick.json")
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        this.am = AnimationManager(File(dataFolder, "animations"), this)
        getCommand("tool").executor = Tool()
        Im.reload(File(dataFolder, "items"))

    }

    fun reload() {
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        am.reload()
        reloadConfig()
    }

}