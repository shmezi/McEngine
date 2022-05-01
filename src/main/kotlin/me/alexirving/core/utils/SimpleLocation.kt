package me.alexirving.core.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Location

data class SimpleLocation(
    var world: String,
    var x: Double,
    var y: Double,
    var z: Double,
    var pitch: Float,
    var yaw: Float
) {
    @JsonIgnore
    fun adaptBukkit() = Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)
}