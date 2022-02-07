package me.alexirving.core.public

import me.alexirving.core.McEngine
import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.getFacing
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
    fun getMcEngine(): McEngine {
        return instance ?: throw IllegalStateException("McEngine has not been loaded yet")
    }


    fun getAnimationManager(): AnimationManager {
        return getMcEngine().am
    }

    /**
     * Play give animation at given location to given players.
     * @param animation animation by name, to play
     * @param location Location to play animation at
     * @param viewers Players to play animation to
     */
    fun playAnimation(animation: String, location: Location, viewers: MutableList<Player>) {
        if (getMcEngine().am.getAnimation(animation) == null)
            throw NullPointerException("Animation \"$animation\" does not exist.")

        AnimationSession(
            getMcEngine(),
            viewers,
            location,
            getMcEngine().am.getAnimation(animation)!!,
            location.getFacing()
        )
    }
}