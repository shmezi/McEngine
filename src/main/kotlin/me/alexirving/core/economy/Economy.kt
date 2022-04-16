/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Economy.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.economy

import me.alexirving.core.EngineManager
import me.alexirving.core.utils.nBZ
import java.util.*

/**
 * Represents an economy.
 */
data class Economy(val id: String, val m: EngineManager) {
    private val ecoMap = mutableMapOf<UUID, Double>()

    fun balTop(): Map<UUID, Double> = ecoMap.toList().sortedBy { it.second }.toMap()

    /**
     * Set the balance of a player
     * @param uuid The UUID of the player
     * @param value The amount to set the balance to
     */
    fun setBal(uuid: UUID, value: Double) {
        ecoMap[uuid] = value
        m.database.getUser(uuid) {
            val u = it
            u.ecos[id] = value
            m.database.updateUser(u)
        }
    }

    /**
     * Load user balance from database to cache
     */
    fun load(uuid: UUID) {
        m.database.getUser(uuid) {
            val u = it.ecos[id]
            if (u == null) {
                it.ecos[this.id] = 0.0
                m.database.updateUser(it)
            }
            ecoMap[it.uuid] = it.ecos[id] ?: 0.0
        }
    }

    /**
     * Unloads a user from the cache
     */
    fun unload(uuid: UUID) = ecoMap.remove(uuid)

    /**
     * Returns the balance of the specified user
     * @param uuid The UUID of the player
     * @return The balance of the player
     * **REMEMBER TO LOAD THE USER BEFORE USING THIS FUNCTION
     */
    fun getBal(uuid: UUID): Double = ecoMap[uuid] ?: 0.0

    /**
     * Adds money to the specified player
     */
    fun addBal(uuid: UUID, add: Double) = setBal(uuid, getBal(uuid) + add)
    /**
     * Removes money from the specified player
     */
    fun subBal(uuid: UUID, remove: Double) = setBal(uuid, (getBal(uuid) - remove).nBZ())
}