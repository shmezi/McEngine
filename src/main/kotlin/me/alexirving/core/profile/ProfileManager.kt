package me.alexirving.core.profile

import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import org.bukkit.entity.Player

class ProfileManager {
    val profiles = mutableMapOf<Player, Profile>()
    fun load(player: Player) {
        profiles[player] = Profile(player.uniqueId, mutableMapOf())

        println("Loaded profile of: \"${player.name}\" with uuid of \"${player.uniqueId}\".".color(Colors.GREEN))

    }

    fun unload(player: Player) {
        profiles.remove(player)
    }

    fun getProfile(player: Player): Profile {

        if (profiles.containsKey(player))
            profiles[player]
        else profiles[player] = Profile(player.uniqueId, mutableMapOf())
        return profiles[player]!!
    }
}

