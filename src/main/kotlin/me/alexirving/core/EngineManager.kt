/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Manager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.channels.ChannelManger
import me.alexirving.core.effects.EffectManager
import me.alexirving.core.effects.effects.*
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.hooks.HookWorldEdit
import me.alexirving.core.mines.MineManager
import me.alexirving.core.mines.PrisonSettings
import me.alexirving.core.points.PointManager
import me.alexirving.core.points.track.Level
import me.alexirving.core.points.track.PointsTrack
import me.alexirving.core.structs.GangData
import me.alexirving.core.structs.UserData
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.pq
import me.alexirving.core.utils.registerListeners
import me.alexirving.lib.database.GroupCachedManager
import me.alexirving.lib.database.nosql.MongoConnection
import me.alexirving.lib.database.nosql.MongoDbCachedCollection
import me.alexirving.lib.database.nosql.MongoUtils
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.io.File
import java.util.*

class EngineManager(val engine: McEngine) : Listener {
    private val config = engine.config
    private val df = engine.dataFolder
    val itemFolder = File(df, "items")
    val aniFolder = File(df, "animations")

    //    val item = ItemManager.reload(itemFolder)
    val point = PointManager(this)
    val effect = EffectManager(this)

    private val connection = MongoConnection(
        if (config.getBoolean("HasLogin")) MongoUtils.defaultClient(
            config.getString("Connection") ?: "mongodb://localhost",
            config.getString("User").pq("DB username found") ?: "",
            config.getString("Pass").pq("DB pass found") ?: ""
        )
        else MongoUtils.defaultClient(config.getString("Connection") ?: "mongodb://localhost"), "McEngine"
    )

    val channel = ChannelManger(
        MongoDbCachedCollection("Channels", ChannelData.default(null), connection), this
    )
    val mine = MineManager(this)

    val user = MongoDbCachedCollection(
        "User", UserData(UUID.randomUUID(), mutableMapOf(), PrisonSettings.default(), null, mutableSetOf()), connection
    ).getManager()


    val gang = GroupCachedManager<UUID, UUID, GangData>(
        MongoDbCachedCollection("Gang", GangData.default(null, "gang"), connection), GangData.default(null, "gang")
    )
    val weHook = HookWorldEdit()

    init {
        println("Registering internal effects:".color(Colors.BLUE))
        effect.register(Speed(), Efficiency(), Fortune(), NighVision(), Nuke(this), Jackhammer(this), Laser(this))
        registerListeners(engine, effect, this)
        reload()
    }


    fun loadPlayer(player: Player) {
        user.get(player.uniqueId) { user ->
            user.channels.forEach { channel.loadUser(it, player.uniqueId) }
            gang.loadUser(user.settings.gang ?: return@get, player.uniqueId)
        }

    }

    fun updateDb() {
        user.update()
    }

    fun unloadPlayer(player: Player) {
        user.get(player.uniqueId) { user ->
            user.channels.forEach { channel.unloadUser(it, player.uniqueId) }
            gang.unloadUser(user.settings.gang ?: return@get, player.uniqueId)
        }
        user.unload(player.uniqueId)
    }

    fun reload() {

        engine.reloadConfig()
        AnimationManager.reload(aniFolder)
        mine.reload()
//        ItemManager.reload(itemFolder)

        for (e in engine.config.getStringList("Ecos")) {
            println("Loading eco of id \"$e\":".color(Colors.BLUE))
            point.create(e)
        }
        val pc = engine.config.getConfigurationSection("Prestiges")
            ?: throw NotFoundException("Prestiges not found in config")
        point.register(PointsTrack("PRESTIGE", this, mutableListOf<Level>().apply {
            for (m in pc.getKeys(false)) {
                this.add(
                    Level(
                        m, pc.getString("$m.Name") ?: continue, pc.getStringList("$m.Cmds"), pc.getDouble("$m.Price")
                    )
                )
            }
        }))

    }

}