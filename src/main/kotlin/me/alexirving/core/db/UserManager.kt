package me.alexirving.core.db

import me.alexirving.core.exceptions.ShmeziFuckedUp
import me.alexirving.core.utils.pq
import java.util.*

/**
 * Stores all cached data of users on the server includes balance.
 */
class UserManager(private val db: Database) {
    private val playerCache = mutableMapOf<UUID, UserData>()
    private val updateCache = mutableSetOf<UUID>()

    fun getUser(uuid: UUID, update: Boolean, async: (userData: UserData) -> Unit) {
        if (playerCache.containsKey(uuid)) async(playerCache[uuid] ?: throw  ShmeziFuckedUp("Not possible."))
        else
            db.getUser(uuid) {
                async(it)
                playerCache[uuid] = it
                "RETRIEVE USER FROM DB!".pq()
            }
        if (update)
            updateCache.add(uuid)
        updateCache.pq("READ")
    }

    fun getUser(uuid: UUID, async: (userData: UserData) -> Unit) = getUser(uuid, false, async)
    fun getUsers(async: (userData: List<UserData>) -> Unit) = db.getUsers { async(it) }

    fun updateDb() {
        if (updateCache.isEmpty()) return
        db.updateUsers(updateCache.map { playerCache[it] ?: throw ShmeziFuckedUp("Not sure how..") })
        updateCache.clear()
    }

    fun clearCacheSafely() {
        updateDb()
        playerCache.clear()
    }

    fun updateUser(userData: UserData) {
        playerCache[userData.uuid] = userData
        updateCache.add(userData.uuid)
    }

    fun unload(uuid: UUID) {
        playerCache.remove(uuid)
    }
}