/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * EcoManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.econemy

import me.alexirving.core.sql.MongoDb
import java.util.*

object EcoManager {
    private val ecos = mutableMapOf<String, Economy>()

    fun create(id: String): Economy {
        ecos[id] = Economy(id)
        return ecos[id] ?: throw NullPointerException("Some weird ass thing happened in EcoManager!")
    }

    fun load(uuid: UUID) {
        for (e in MongoDb.getUser(uuid.toString()).ecos)
            ecos[e.key]?.load(uuid, e.value)
    }

    fun delete(id: String) {
            ecos.remove(id)
    }

    fun getEco(id: String) = ecos[id]
}