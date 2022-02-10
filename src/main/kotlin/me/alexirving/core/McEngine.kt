package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.animation.cmds.AnimationCMD
import me.alexirving.core.animation.cmds.McEngineReloadCMD
import me.alexirving.core.commands.Test
import me.alexirving.core.items.ItemManager
import me.alexirving.core.utils.copyOver
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class McEngine : JavaPlugin() {

    val im = ItemManager()
    lateinit var am: AnimationManager
    override fun onEnable() {
        McEngineAPI.instance = this
        saveDefaultConfig()
        copyOver(dataFolder, "items.yml", "animations", "animations/Default.yml")
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        this.am = AnimationManager(File(dataFolder, "animations"), this)
        getCommand("test")?.executor = Test()
        getCommand("animation")?.executor = AnimationCMD(this, am)
        getCommand("mcenginereloadcmd")?.executor = McEngineReloadCMD(this)
    }

    fun reload() {
        im.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        am.reload()
        reloadConfig()
    }

}