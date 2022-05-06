package me.alexirving.core.database

import java.util.*

open class GroupCachedManager<T : Cacheable>(db: Database<Cacheable>, template: T) : CachedManager<T>(db, template) {
    protected val groups = mutableMapOf<UUID, MutableSet<UUID>>()
    private val userCache = mutableMapOf<UUID, MutableSet<UUID>>()

    fun isUserCached(uuid: UUID) = userCache.contains(uuid)
    fun isGroupCached(uuid: UUID) = groups.containsKey(uuid)
    fun loadUser(groupId: UUID, userID: UUID) {
        groups.getOrPut(groupId) { mutableSetOf() }.add(userID)
        userCache.getOrPut(userID) { mutableSetOf() }.add(groupId)
        if (!super.cancelUnload(groupId))
            super.get(groupId) {

            }
    }

    fun getOfUser(uuid: UUID) {
        val cached = mutableSetOf<T>()
        userCache[uuid]?.forEach { cId ->
            get(cId) {
                cached.add(it)
            }
        }
    }

    fun unloadUser(groupId: UUID, userID: UUID) {
        if (!groups.containsKey(groupId)) return
        groups[groupId]?.remove(userID)
        if ((groups[groupId].isNullOrEmpty()))
            super.unload(groupId)
        userCache.remove(userID)
    }

}