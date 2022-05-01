package me.alexirving.core.db

import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.effects.Effect
import me.alexirving.core.mines.PrisonSettings
import java.util.*

/**
 * Represents a user's data in the database.
 * @param uuid The player's ID.
 * @param points The player's ecos (for each currency).
 * @param currentChannel The player's selected channel to chat in, if any.
 */
data class UserData(
    val uuid: String,
    val points: MutableMap<String, Double>,
    val settings: PrisonSettings,
    var currentChannel: UUID?,
    val channels: MutableSet<UUID>,

    ) {
    @JsonIgnore
    val activeEffects = mutableMapOf<Effect, Int>()

    @JsonIgnore
    fun getId(): UUID = UUID.fromString(uuid)
}