package me.alexirving.core.actions.data

import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.utils.Direction
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.entity.Player

@Suppress("UNCHECKED_CAST")
class ActionDataValue(private val data: Any) {
    fun asPlayer() = data as Player
    fun asPlayers() = data as List<Player>
    fun asString() = data as String
    fun asInt() = data as Int
    fun asDouble() = data as Double
    fun asFloat() = data as Float

    fun asSession() = data as AnimationSession
    fun <T> asType() = data as T
    fun <T> asList() = data as List<T>
    fun asLocation() = data as Location
    fun asDirection() = data as Direction
    fun asByte() = data as Byte
    fun asEffect() = data as Effect
}