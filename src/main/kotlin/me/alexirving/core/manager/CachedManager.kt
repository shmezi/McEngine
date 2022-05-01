//package me.alexirving.core.manager
//
//import java.util.*
//
//abstract class CachedManager< T> {
//    val cache = mutableMapOf<UUID, T>()
//    val updates = mutableSetOf<UUID>()
//
//    fun get(key: UUID, success: (value: T) -> Unit) {
//        if (cache.containsKey(key)) success(cache[key] ?: return)
//        else
//
//    }
//}