package me.alexirving.core.channels

import org.bukkit.entity.Player
import java.util.*

data class Channel(
    val id: UUID,
    val name: String,
    val suffix: String,
    val groups: MutableList<Group>,
    val owner: Player,
    val participants: MutableMap<Player, Group>
) {
}