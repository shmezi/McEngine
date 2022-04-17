package me.alexirving.core.profile

import me.alexirving.core.channels.Channel
import me.alexirving.core.effects.Effect
import me.alexirving.core.mines.PrivateMineSettings
import org.bukkit.entity.Player

/**
 * Cached data of a player
 * @param player Player that this [Profile] wraps
 * @param activeEffects current active effects.
 * @param selectedChannel current channel player is looking in
 */
data class Profile(
    val player: Player,
    val activeEffects: MutableMap<Effect, Int>,
    var privateMineSettings: PrivateMineSettings,
    var selectedChannel: Channel?,
)