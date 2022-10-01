package me.alexirving.core.hooks

import org.bukkit.Bukkit

abstract class Hook(plugin: String) {
    val enabled: Boolean

    init {
        enabled = if (!Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            println("Hook disabled for plugin \"$plugin\"")
            false
        } else
            true
    }
}