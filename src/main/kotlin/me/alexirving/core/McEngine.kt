/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * McEngine.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import com.github.retrooper.packetevents.PacketEvents
import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import dev.triumphteam.cmd.core.suggestion.SuggestionKey
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.commands.*
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
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class McEngine : JavaPlugin() {

    override fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.buildNoCache(this))
        PacketEvents.getAPI().settings.debug(true).checkForUpdates(true)
        PacketEvents.getAPI().load()

    }

    var manager: EngineManager? = null
    override fun onEnable() {
        manager = EngineManager(this)
        val manager = manager ?: return
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
        cmm.registerArgument(ChannelData::class.java) { player, id ->
            {
                var cd: ChannelData? = null
                if (player is Player) {
                    manager.channel.queryChannel(player, id) {
                        cd = it
                    }
                }
                cd
            }
        }
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


        cmm.registerSuggestion(SuggestionKey.of("player-x")) { sender, _ ->
            if (sender is Player) {
                mutableListOf(sender.player?.getTargetBlock(mutableSetOf(Material.AIR), 10)?.x.toString())
            } else
                mutableListOf()
        }
        cmm.registerSuggestion(SuggestionKey.of("player-y")) { sender, _ ->
            if (sender is Player) {
                mutableListOf(sender.player?.getTargetBlock(mutableSetOf(Material.AIR), 10)?.y.toString())
            } else
                mutableListOf()
        }
        cmm.registerSuggestion(SuggestionKey.of("player-z")) { sender, arg ->
            if (sender is Player) {
                mutableListOf(
                    sender.player?.getTargetBlock(mutableSetOf(Material.AIR), 10)?.z.toString()
                )
            } else
                mutableListOf()
        }
        cmm.registerCommand(
            CMDEngine(manager),
            CMDEconomy(),
            CMDAnimation(this),
            CMDMine(manager),
            CMDGang(manager)
        )



        registerListeners(this, PlayerJoin(manager), PlayerInteract(), PlayerLeave(manager))
        Bukkit.getScheduler()
            .scheduleSyncRepeatingTask(
                this,
                { manager.user.updateDb("Schedule") }, 0L, config.getLong("AutoSave") ?: 12000L
            )
        PacketEvents.getAPI().init()
    }

    override fun onDisable() {
        manager?.user?.updateDb("Disable")
    }
}