/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import com.fasterxml.jackson.databind.module.SimpleModule
import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.commands.AnimationCMD
import me.alexirving.core.commands.Tool
import me.alexirving.core.econemy.EcoManager
import me.alexirving.core.events.PlayerInteract
import me.alexirving.core.events.PlayerJoin
import me.alexirving.core.hooks.Papi
import me.alexirving.core.legacyItems.LegacyItemManager
import me.alexirving.core.sql.MongoDb
import me.alexirving.core.utils.copyOver
import me.alexirving.core.utils.registerListeners
import org.bstats.bukkit.Metrics
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import  me.alexirving.core.item.ItemManager as Im

class McEngine : JavaPlugin() {

    val lim = LegacyItemManager()
    lateinit var am: AnimationManager
    val em = EcoManager
    override fun onEnable() {

        /**
         * Basic startup
         */
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
        McEngineAPI.instance = this
        saveDefaultConfig()
        Metrics(this, 14580)
        copyOver(dataFolder, "items.yml", "animations", "items", "animations/Default.yml", "items/SuperPick.json")
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null)
            Papi(this).register()

        /**
         * Eco setup
         */
//        if (server.pluginManager.getPlugin("Vault") != null)


        /*
         * Managers loading
         */
        println("Does it exist: ${File(dataFolder, "items").exists()}")
        Im.reload(File(dataFolder, "items"))
        lim.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        this.am = AnimationManager(File(dataFolder, "animations"), this)
        MongoDb.init(config.getString("MongoDb") ?: "mongodb://localhost")
        for (e in config.getStringList("Ecos"))
            em.create(e)
        registerListeners(this, PlayerJoin(), PlayerInteract())

        /**
         * Commands
         */
        getCommand("engine")?.setExecutor(Tool(this))
        getCommand("animation")?.setExecutor(AnimationCMD(this))

    }

    fun reload() {
        lim.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        am.reload()
        reloadConfig()
        Im.reload(File(dataFolder, "items"))
        MongoDb.init(config.getString("MongoDb") ?: "mongodb://localhost")

    }

}