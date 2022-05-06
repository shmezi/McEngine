package me.alexirving.core.db

import me.alexirving.core.exceptions.ShmeziFuckedUp
import me.alexirving.core.utils.pq
import org.bukkit.Warning
import org.bukkit.entity.Player
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
            db.dbGetUser(uuid) {
                playerCache[uuid] = it
                async(it)
            }
        if (update)
            updateCache.add(uuid)
    }

    fun getUser(uuid: UUID, async: (userData: UserData) -> Unit) = getUser(uuid, false, async)

    fun getUser(player: Player, async: (userData: UserData) -> Unit) = getUser(player.uniqueId, async)
    fun getUser(player: Player, update: Boolean, async: (userData: UserData) -> Unit) =
        getUser(player.uniqueId, update, async)

    fun getUsers(async: (userData: List<UserData>) -> Unit) = db.dbGetUsers { async(it) }

    fun updateDb(reason: String) {
        if (updateCache.isEmpty()) return
        db.dbUpdateUsers(updateCache.map { playerCache[it] ?: throw ShmeziFuckedUp("Not sure how..") })
        "Now updating database! ${updateCache.size} items will be updated! for reason: $reason".pq()
        updateCache.clear()
    }

    fun clearCacheSafely() {
        updateDb("Clear cache safely")
        playerCache.clear()
    }

    fun updateUser(userData: UserData) {
        "UPDATING USER with uuid of ${userData.uuid}".pq()
        playerCache[userData.getId()] = userData
        updateCache.add(userData.getId())
    }

    @Deprecated("DO NOT USE, this will not save to db!")
    fun unload(uuid: UUID) {
        playerCache.remove(uuid)
    }
}