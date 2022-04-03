/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Economy.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.econemy

import me.alexirving.core.sql.MongoDb
import me.alexirving.core.utils.nBZ
import java.util.*

data class Economy(val id: String) {
    private val ecoMap = mutableMapOf<UUID, Double>()

    init {
//        MongoDb.getDb().getCollection("eco") ?: MongoDb.getDb().createCollection("eco")
    }

    private fun setValue(uuid: String, value: Double) {
//        MongoDb.getUser(uuid).setBal(id, value)
    }

    fun setBal(uuid: UUID, eco: Double): Double {
        setValue(uuid.toString(), eco)
        ecoMap[uuid] = eco

        return ecoMap[uuid] ?: 0.0
    }

    fun getBal(player: UUID): Double = ecoMap[player] ?: 0.0

    fun addBal(player: UUID, add: Double) = setBal(player, getBal(player) + add)

    fun subBal(player: UUID, remove: Double) = setBal(player, (getBal(player) - remove).nBZ())

    fun load(player: UUID, value: Double) {
        ecoMap[player] = value
    }
}