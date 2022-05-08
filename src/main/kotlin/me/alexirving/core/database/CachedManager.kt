package me.alexirving.core.database

import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.pq
import java.util.*

/**
 * A managed set of data that is cached.
 * @param db The database to use for actions
 */
open class CachedManager<T : Cacheable>(private val db: Database<Cacheable>, private val template: T) {
    protected val cache = mutableMapOf<UUID, T>() //Cache of all currently loaded users
    private val updates = mutableSetOf<UUID>() //The users that their data has changed and db needs to be changed
    private val removals = mutableSetOf<UUID>() //The users that will be remove from the db next update
    private val cacheRemovals = mutableSetOf<UUID>() //The users that will be removed next cache update

    /**
     * Check if a user is currently cached
     * @param uuid - UUID of the user
     * @return if the user is currently cached ( does not include users that will be removed soon by an update
     */
    open fun isCached(uuid: UUID) = cache.containsKey(uuid) && !cacheRemovals.contains(uuid)

    /**
     * Get data from the database.
     * This will load/create the user if they do are not currently cached/existent.
     * @param uuid - UUID of user
     * @param update - Should the database update the data
     * @param async - Will be called once data is retrieved
     */
    open fun get(uuid: UUID, update: Boolean, async: (value: T) -> Unit) {
        if (cache.containsKey(uuid)) {
            val q = cache[uuid] ?: return
            async(q)//Don't move down as I want to make sure the data is changed before the database update.
        } else {
            db.dbGet(uuid) {
                if (it == null) {
                    val t = template.clone().apply { this.uuid = uuid.toString() } as T
                    cache[t.getUUID()] = t
                    async(t) //Don't move down as I want to make sure the data is changed before the database update.
                    updates.add(t.getUUID())
                } else {
                    cache[uuid] = it as T
                    async(it)
                }
            }

        }
        if (update)
            updates.add(uuid)
    }

    /**
     * Get data from the database.
     * This will load/create the user if they do are not currently cached/existent.
     * @param uuid - UUID of user
     * @param async - Will be called once data is retrieved
     */
    open fun get(uuid: UUID, async: (value: T) -> Unit) = get(uuid, false, async)

    /**
     * Get data from the database. This will load the user if they aren't currently loaded into cache
     * Note that this differs from [get] as it will not create the user if they do not exist in the db
     * @param uuid - UUID of user
     * @param success - Will be called if the data is retrieved
     * @param failure - Will be called if the user is not in the database
     */
    open fun getIfInDb(uuid: UUID, success: (value: T) -> Unit, failure: () -> Unit) {
        db.dbGet(uuid) {
            if (it != null)
                success(it as T)
            else
                failure()
        }
    }

    /**
     * Get data from the database. This will load the user if they aren't currently loaded into cache
     * Note that this differs from [get] as it will not create the user if they do not exist in the db
     * @param uuid - UUID of user
     * @param async - Will be called if the data of the user exists
     */
    open fun getIfInDb(uuid: UUID, async: (value: T) -> Unit) = getIfInDb(uuid, async) {}

    /**
     * Checks if a user is in the database.
     * @param uuid - UUID of user
     * @param async - Called with the result
     */
    open fun doesExist(uuid: UUID, async: (bool: Boolean) -> Unit) {
        db.dbGet(uuid) {
            async(it != null)
        }
    }

    /**
     * Deletes the user from the database
     */
    open fun delete(uuid: UUID) {
        if (cache.containsKey(uuid)) cache.remove(uuid)
        removals.add(uuid)
    }

    /**
     * Runs an update job, this will update all data in the database from cache and clear caches.
     */
    open fun update() {
        println("Running db update, updating ${updates.size} items!".color(Colors.BLUE))
        for (u in updates) {
            db.dbUpdate(cache[u] ?: continue)
        }
        for (d in removals)
            db.dbUpdate(cache[d] ?: continue)
        for (cd in cacheRemovals)
            cache.remove(cd)
        updates.clear()
        removals.clear()
        cacheRemovals.clear()
    }

    /**
     * Safely unload a user from the cache
     */
    open fun unload(uuid: UUID) {
        cacheRemovals.add(uuid)
    }

    /**
     * Cancel an unload job.
     * The use of this is to reduce uncaching data, can be run for example when player joins before loading data.
     * @return returns true if the user was not cleared from the cache yet
     */
    open fun cancelUnload(uuid: UUID) = cacheRemovals.remove(uuid)


}