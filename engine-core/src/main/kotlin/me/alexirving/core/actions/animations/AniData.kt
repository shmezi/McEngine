package me.alexirving.core.actions.animations

import me.alexirving.core.actions.data.ActionData

class AniData : ActionData() {
    fun getSession() = this.data["session"]?.asSession()
        ?: throw RuntimeException("Could not get session, FrameData should be made exclusively from internal functions!")

    fun getPlayers() = data["players"]?.asPlayers()
        ?: throw RuntimeException("Could not get session, FrameData should be made exclusively from internal functions!")
    fun getstandMap() = getSession().standMap
}