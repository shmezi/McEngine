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
import me.alexirving.core.channels.ChannelManger
import me.alexirving.core.db.Database
import me.alexirving.core.db.nosql.MongoDb
import me.alexirving.core.economy.EcoManager
import me.alexirving.core.effects.EffectManager
import me.alexirving.core.effects.effects.Efficiency
import me.alexirving.core.effects.effects.Fortune
import me.alexirving.core.effects.effects.NighVision
import me.alexirving.core.effects.effects.Speed
import me.alexirving.core.hooks.HookWorldEdit
import me.alexirving.core.item.ItemManager
import me.alexirving.core.mines.MineManager
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.profile.ProfileManager
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.registerListeners
import org.bukkit.event.Listener
import java.io.File

class EngineManager(val engine: McEngine) : Listener {
    private val df = engine.dataFolder
    val item = ItemManager(File(df, "items"))
    val eco = EcoManager(this)
    val effect = EffectManager(this)
    val profile = ProfileManager()
    val packet = PacketManager()
    val channel = ChannelManger(this)
    val mines = MineManager(this)
    val gson = Gson()
    val animation = AnimationManager(File(df, "animations"), this)
    val database = MongoDb(engine.config.getString("connection") ?: "MongoDb://localhost") as Database
    val weHook = HookWorldEdit()

    init {
        println("Registering internal effects:".color(Colors.BLUE))
        effect.register(Speed(), Efficiency(), Fortune(), NighVision())
        registerListeners(engine, effect, channel, this)
        reload()
    }

    fun reload() {
        engine.reloadConfig()
        mines.reload()
        database.reload(engine.config.getString("connection") ?: "MongoDb://localhost")
        item.reload()
        animation.reload()
        for (e in engine.config.getStringList("Ecos")) {
            println("Loading eco of id \"$e\":".color(Colors.BLUE))
            eco.create(e)
        }
    }
}