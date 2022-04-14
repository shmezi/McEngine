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
import me.alexirving.core.economy.EcoManager
import me.alexirving.core.effects.EffectManager
import me.alexirving.core.effects.effects.Efficiency
import me.alexirving.core.effects.effects.Fortune
import me.alexirving.core.effects.effects.Speed
import me.alexirving.core.item.ItemManager
import me.alexirving.core.packets.PacketManager
import me.alexirving.core.profile.ProfileManager
import me.alexirving.core.utils.registerListeners
import java.io.File

class EngineManager(val engine: McEngine) {
    private val df = engine.dataFolder
    val item = ItemManager(File(df, "items"))
    val eco = EcoManager()
    val effect = EffectManager(this)
    val profile = ProfileManager()
    val packet = PacketManager()
    val gson = Gson()
    val animation = AnimationManager(File(df, "animations"), this)

    init {
        effect.register(Speed(), Efficiency(), Fortune())
        registerListeners(engine, effect)
    }

    fun reload() {
        engine.reloadConfig()
        item.reload()
        animation.reload()
        for (e in engine.config.getStringList("Ecos"))
            eco.create(e)
    }
}