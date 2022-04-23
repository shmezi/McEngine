/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Economy.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.economy

import me.alexirving.core.EngineManager
import me.alexirving.core.db.UserData
import me.alexirving.core.utils.nBZ
import java.util.*

/**
 * Represents an economy.
 */
data class Economy(val id: String, val m: EngineManager) {

    fun balTop(async: (balTop: List<UserData>) -> Unit) {
        m.user.getUsers { users ->
            async(users.sortedBy { it.ecos[id] })
        }
    }

    /**
     * Set the balance of a player
     * @param uuid The UUID of the player
     * @param value The amount to set the balance to
     */
    fun setBal(uuid: UUID, value: Double) {
        m.user.getUser(uuid, true) {
            it.ecos[id] = value
        }
    }


    /**
     * Returns the balance of the specified user
     * @param uuid The UUID of the player
     * @return The balance of the player
     * **REMEMBER TO LOAD THE USER BEFORE USING THIS FUNCTION
     */
    fun getBal(uuid: UUID, async: (balance: Double) -> Unit) = m.user.getUser(uuid) {
        async(it.ecos[id] ?: 0.0)
    }

    /**
     * Adds money to the specified player
     */
    fun addBal(uuid: UUID, add: Double) {
        getBal(uuid) {
            setBal(uuid, it + add)
        }
    }

    /**
     * Removes money from the specified player
     */
    fun subBal(uuid: UUID, remove: Double) {
        getBal(uuid) {
            setBal(uuid, (it - remove).nBZ())
        }
    }
}