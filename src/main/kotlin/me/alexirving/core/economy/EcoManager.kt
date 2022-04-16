/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * EcoManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.economy

import me.alexirving.core.EngineManager
import java.util.*

class EcoManager(val m: EngineManager) {
    private val ecos = mutableMapOf<String, Economy>()

    fun create(id: String): Economy {
        val e = Economy(id, m)
        ecos[id] = e
        return e
    }

    fun load(uuid: UUID) {
        for (e in ecos.values) {
            e.load(uuid)
        }
    }
    fun unload(uuid: UUID) {
        for (e in ecos.values) {
            e.unload(uuid)
        }
    }
    fun delete(id: String) {
        ecos.remove(id)
    }

    fun getEco(id: String) = ecos[id]
    fun getEcoIds() = ecos.keys
}