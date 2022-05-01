/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.commands.CMDAnimation
import me.alexirving.core.commands.CMDEconomy
import me.alexirving.core.commands.CMDEngine
import me.alexirving.core.commands.CMDMine
import me.alexirving.core.effects.Effect
import me.alexirving.core.events.PlayerInteract
import me.alexirving.core.events.PlayerJoin
import me.alexirving.core.events.PlayerLeave
import me.alexirving.core.hooks.HookPapi
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.points.Points
import me.alexirving.core.utils.copyOver
import me.alexirving.core.utils.registerListeners
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin


class McEngine : JavaPlugin() {

    lateinit var manager: EngineManager
    override fun onEnable() {
        manager = EngineManager(this)
        val cmm = BukkitCommandManager.create(this)

        /**
         * Basic startup
         */

        saveDefaultConfig()

        McEngineAPI.instance = this
        Metrics(this, 14580)
        copyOver(dataFolder, "animations", "items", "animations/Default.yml", "items/SuperPick.json")
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null)
            HookPapi(this).register()


        /**
         * Command registering
         */


        val im = manager.item
        cmm.registerArgument(BaseItem::class.java) { _, itemId ->
            im.bases[itemId]
        }
        cmm.registerSuggestion(BaseItem::class.java) { _, _ ->
            im.bases.keys.toList()
        }
        val ecoM = manager.point
        cmm.registerArgument(Points::class.java) { _, eco ->
            ecoM.getPoints(eco)
        }
        cmm.registerSuggestion(Points::class.java) { _, _ ->
            ecoM.getPointsId().toList()
        }
        val ani = manager.animation
        cmm.registerArgument(Animation::class.java) { _, animation ->
            ani.getAnimation(animation)
        }
        cmm.registerSuggestion(Animation::class.java) { _, _ ->
            ani.getAnimationNames().toList()
        }

        cmm.registerArgument(Effect::class.java) { _, effect ->
            manager.effect.getEffectById(effect)
        }
        cmm.registerSuggestion(Effect::class.java) { _, _ ->
            manager.effect.getEffectIds()
        }
        cmm.registerSuggestion(World::class.java) { _, _ ->
            Bukkit.getWorlds().map { it.name }
        }


        cmm.registerCommand(CMDEngine(manager), CMDEconomy(), CMDAnimation(this), CMDMine(manager))


//        MongoDb.init(config.getString("MongoDb") ?: "mongodb://localhost")

        registerListeners(this, PlayerJoin(manager), PlayerInteract(), PlayerLeave(manager))
        Bukkit.getScheduler()
            .scheduleSyncRepeatingTask(
                this,
                { manager.user.updateDb("Schedule") }, 0L, config.getLong("AutoSave") ?: 12000L
            )
    }

    override fun onDisable() {
        manager.user.updateDb("Disable")
    }
}