package me.alexirving.core.structs

import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.effects.Effect
import me.alexirving.core.mines.PrisonSettings
import me.alexirving.lib.database.Cacheable
import java.util.*

/**
 * Represents a user's data in the database.
 * @param uuid The player's unique ID.
 * @param points The player's ecos (for each currency).
 * @param currentChannel The player's selected channel to chat in, if any.
 */
class UserData(
    uuid: UUID,
    val points: MutableMap<String, Double>,
    val settings: PrisonSettings,
    var currentChannel: UUID?,
    val channels: MutableSet<UUID>,
) : Cacheable<UUID>(uuid) {
    @JsonIgnore
    val activeEffects = mutableMapOf<Effect, Int>()
}