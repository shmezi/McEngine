package me.alexirving.core.actions

import me.alexirving.core.EngineManager
import me.alexirving.core.animation.objects.AnimationSession

/**
 * Utility class for ease of use with the animation system
 */
abstract class AniAction(m: EngineManager, args: Map<String, Any>) : Action(m, args) {

    override fun run(data: MutableMap<String, Any>) {
        run(data["session"] as AnimationSession, data)
    }

    abstract fun run(session: AnimationSession, data: MutableMap<String, Any>)

}