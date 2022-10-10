package me.alexirving.core.structs

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import me.alexirving.core.effects.Effect
import me.alexirving.lib.database.Cacheable
import java.util.*

/**
 * Represents a user's data in the database.
 * @param uuid The player's unique ID.
 * @param points The player's ecos (for each currency).
 * @param currentChannel The player's selected channel to chat in, if any.
 */
class UserData(
    @JsonProperty("identifier")
    uuid: UUID,
    val points: MutableMap<String, Double>,
    var currentChannel: UUID?,
    val channels: MutableSet<UUID>,
) : Cacheable<UUID>(uuid) {
    @JsonIgnore
    val activeEffects = mutableMapOf<Effect, Int>()
}