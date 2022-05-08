/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Manager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import com.google.gson.Gson
import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.channels.ChannelManger
import me.alexirving.core.database.CachedManager
import me.alexirving.core.database.GroupCachedManager
import me.alexirving.core.database.nosql.MongoDbCachedDatabase
import me.alexirving.core.database.structs.GangData
import me.alexirving.core.database.structs.UserData
import me.alexirving.core.effects.EffectManager
import me.alexirving.core.effects.effects.*
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.hooks.HookWorldEdit
import me.alexirving.core.item.ItemManager
import me.alexirving.core.mines.MineManager
import me.alexirving.core.mines.PrisonSettings
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.points.PointManager
import me.alexirving.core.points.track.Level
import me.alexirving.core.points.track.PointsTrack
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.registerListeners
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.io.File

class EngineManager(val engine: McEngine) : Listener {
    private var connection: String = "mongodb://localhost"
    private val df = engine.dataFolder
    val item = ItemManager(File(df, "items"))
    val point = PointManager(this)
    val effect = EffectManager(this)
    val packet = PacketManager()
    val channel = ChannelManger(
        MongoDbCachedDatabase("Channel", ChannelData::class.java, connection),this)
    val mine = MineManager(this)
    val gson = Gson()
    val animation = AnimationManager(File(df, "animations"), this)
    val user = CachedManager(
        MongoDbCachedDatabase("User", UserData::class.java, connection), UserData(
            mutableMapOf(),
            PrisonSettings.default(), null, mutableSetOf()
        )
    )
    val gang = GroupCachedManager(
        MongoDbCachedDatabase("Gang", GangData::class.java, connection),
        GangData.default(null, "Gang")
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
        channel.update()
        gang.update()
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
        mine.reload()
        item.reload()
        animation.reload()

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
                        m,
                        pc.getString("$m.Name") ?: continue,
                        pc.getStringList("$m.Cmds"),
                        pc.getDouble("$m.Price")
                    )
                )
            }
        }))

    }
}