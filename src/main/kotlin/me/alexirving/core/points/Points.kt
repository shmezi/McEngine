/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Economy.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.points

import me.alexirving.core.EngineManager
import me.alexirving.core.database.structs.UserData
import me.alexirving.core.utils.nBZ
import java.util.*

/**
 * Represents a points system such as an economy.
 */
open class Points(val id: String, private val m: EngineManager) {

    open fun pointTop(async: (pointTop: List<UserData>) -> Unit) {
//        m.user.getUsers { users ->
//            async(users.sortedBy { it.points[id] })
//        }
    }

    /**
     * Set the points of a player
     * @param uuid The UUID of the player
     * @param value The amount to set the player's points to
     */
    open fun setPoints(uuid: UUID, value: Double) {
        m.user.get(uuid, true) {
            it.points[id] = value
        }
    }


    /**
     * Returns the points of the specified user
     * @param uuid The UUID of the player
     * @return The points of the player
     */
    open fun getPoints(uuid: UUID, async: (balance: Double) -> Unit) = m.user.get(uuid) {
        async(it.points[id] ?: 0.0)
    }

    /**
     * Adds money to the specified player
     */
    open fun addPoints(uuid: UUID, add: Double) {
        getPoints(uuid) {
            setPoints(uuid, it + add)
        }
    }

    /**
     * Removes money from the specified player
     */
    open fun removePoints(uuid: UUID, remove: Double) {
        getPoints(uuid) {
            setPoints(uuid, (it - remove).nBZ())
        }
    }
}