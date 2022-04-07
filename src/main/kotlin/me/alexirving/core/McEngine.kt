/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.utils.Direction
import me.alexirving.core.commands.CMDAnimation
import me.alexirving.core.commands.CMDEconomy
import me.alexirving.core.commands.CMDEngine
import me.alexirving.core.economy.EcoManager
import me.alexirving.core.economy.Economy
import me.alexirving.core.events.PlayerInteract
import me.alexirving.core.events.PlayerJoin
import me.alexirving.core.hooks.Papi
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.legacyItems.LegacyItemManager
import me.alexirving.core.utils.copyOver
import me.alexirving.core.utils.registerListeners
import me.mattstudios.mf.base.CommandManager
import me.mattstudios.mf.base.components.TypeResult
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import me.alexirving.core.item.ItemManager as Im


class McEngine : JavaPlugin() {

    val lim = LegacyItemManager()
    lateinit var am: AnimationManager
    val em = EcoManager
    override fun onEnable() {
        val cmm = CommandManager(this)
        /**
         * Basic startup
         */
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
        saveDefaultConfig()

        McEngineAPI.instance = this
        Metrics(this, 14580)
        copyOver(dataFolder, "items.yml", "animations", "items", "animations/Default.yml", "items/SuperPick.json")
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null)
            Papi(this).register()
        Im.reload(File(dataFolder, "items"))



        cmm.parameterHandler.register(BaseItem::class.java) {
            if (!Im.bases.containsKey(it)) TypeResult(it)
            else TypeResult(Im.bases[it], it)
        }
        cmm.completionHandler.register("#baseIds") {
            Im.bases.keys.toList()
        }

        cmm.parameterHandler.register(Economy::class.java) {
            if (EcoManager.getEco(it.toString()) == null) TypeResult(it)
            else TypeResult((EcoManager.getEco(it.toString())), it)
        }
        cmm.completionHandler.register("#ecoIds") {
            EcoManager.getEcoIds().toList()
        }
        cmm.parameterHandler.register(Animation::class.java) {
            if (am.getAnimation(it.toString()) == null) TypeResult(it)
            else TypeResult((am.getAnimation(it.toString())), it)
        }
        cmm.parameterHandler.register(Direction::class.java) { any ->
            if (Direction.values().map { it.name }.contains(any.toString().uppercase()))
                TypeResult(Direction.valueOf(any.toString().uppercase()), any)
            else
                TypeResult(any)

        }
        cmm.completionHandler.register("#animationIds") {
            am.getAnimationNames().toList()
        }
        cmm.completionHandler.register("#worlds") {
            Bukkit.getWorlds().map { it.name }
        }

        cmm.register(CMDEngine(this), CMDEconomy(), CMDAnimation(this))
        /**
         * Eco setup
         */
//        if (server.pluginManager.getPlugin("Vault") != null)


        /*
         * Managers loading
         */
        lim.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        this.am = AnimationManager(File(dataFolder, "animations"), this)
//        MongoDb.init(config.getString("MongoDb") ?: "mongodb://localhost")
        for (e in config.getStringList("Ecos"))
            em.create(e)
        registerListeners(this, PlayerJoin(), PlayerInteract())
    }

    fun reload() {
        lim.reload(YamlConfiguration.loadConfiguration(File(dataFolder, "items.yml")))
        am.reload()
        reloadConfig()
        Im.reload(File(dataFolder, "items"))
//        MongoDb.init(config.getString("MongoDb") ?: "mongodb://localhost")

    }

}