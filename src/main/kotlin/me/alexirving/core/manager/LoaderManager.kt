package me.alexirving.core.manager

import me.alexirving.core.exceptions.AlreadyExists

abstract class LoaderManager<T : Loadable>(private val id: String) {
    private val loaded = mutableMapOf<String, T>()//ID, <T>
    open fun load(vararg toLoad: T) {
        toLoad.forEach {
            if (loaded.containsKey(it.id)) throw AlreadyExists("Item with id ${it.id} already exists in $id!")
            loaded[it.id] = it
        }
    }

    open fun unload(vararg toLoad: T) {
        toLoad.forEach { loaded.remove(it.id) }
    }

    open fun getById(id: String) = loaded[id]

    open fun getIds() = loaded.keys
}