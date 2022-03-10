package me.alexirving.core

import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.getFacing
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Public API for [McEngine]
 */
object McEngineAPI {
    var instance: McEngine? = null

    /**
     * Get instance of plugin
     */
    fun getMcEngine(run: () -> Unit) =
        if (instance != null) {
            run()
            instance ?: throw IllegalStateException("McEngine has not been loaded yet [0]")
        } else
            throw IllegalStateException("McEngine has not been loaded yet [1]")


    fun getMcEngine() = instance ?: throw IllegalStateException("McEngine has not been loaded yet")


    fun getAnimationManager() = getMcEngine().am


    /**
     * Play give animation at given location to given players.
     * @param animation animation by name, to play
     * @param location Location to play animation at
     * @param viewers Players to play animation to
     */
    fun playAnimation(animation: String, location: Location, viewers: MutableList<Player>) {
        getMcEngine().am.getAnimation(animation)
            ?: throw NullPointerException("Animation \"$animation\" does not exist.")

        AnimationSession(
            getMcEngine(),
            viewers,
            location,
            getMcEngine().am.getAnimation(animation)!!,
            location.getFacing()
        )
    }

    fun reload() = instance?.reload()
}