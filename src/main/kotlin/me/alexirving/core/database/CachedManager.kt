package me.alexirving.core.database

import me.alexirving.core.utils.pq
import java.util.*

open class CachedManager<T : Cacheable>(private val db: Database<Cacheable>, private val template: T) {
    protected val cache = mutableMapOf<UUID, T>() //Cache of all currently loaded users
    private val updates = mutableSetOf<UUID>() //The users that their data has changed and db needs to be changed
    private val removals = mutableSetOf<UUID>() //The users that will be remove from the db next update
    private val cacheRemovals = mutableSetOf<UUID>() //The users that will be removed next cache update

    open fun isCached(key: UUID) = cache.containsKey(key) && !cacheRemovals.contains(key)

    open fun get(key: UUID, update: Boolean, async: (value: T) -> Unit) {
        if (cache.containsKey(key)) {
            "test".pq(0)
            val q = cache[key] ?: return
            async(q)//Don't move down as I want to make sure the data is changed before the database update.
        } else {
            db.dbGet(key) {
                if (it == null) {
                    val t = template.clone().apply { uuid = key.toString() } as T
                    cache[t.getUUID()] = t
                    async(t) //Don't move down as I want to make sure the data is changed before the database update.
                    updates.add(t.getUUID())
                } else {
                    cache[key] = it as T
                    async(it)
                }
            }

        }
        if (update)
            updates.add(key)
    }

    open fun get(key: UUID, async: (value: T) -> Unit) = get(key, false, async)


    open fun getIfInDb(key: UUID, success: (value: T) -> Unit, failure: () -> Unit) {
        db.dbGet(key) {
            if (it != null)
                success(it as T)
            else
                failure()
        }
    }


    open fun getIfInDb(key: UUID, async: (value: T) -> Unit) = getIfInDb(key, async) {}

    open fun doesExist(key: UUID, async: (bool: Boolean) -> Unit) {
        db.dbGet(key) {
            async(it != null)
        }
    }

    open fun delete(uuid: UUID) {
        if (cache.containsKey(uuid)) cache.remove(uuid)
        removals.add(uuid)
    }

    open fun update() {
        "Running update, updating ${updates.size} items!".pq()
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

    open fun unload(uuid: UUID) {
        cacheRemovals.add(uuid)
    }

    open fun cancelUnload(uuid: UUID) = cacheRemovals.remove(uuid)


}