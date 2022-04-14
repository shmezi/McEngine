package me.alexirving.core.channels

import org.bukkit.entity.Player
import java.util.*

data class Channel(val id: UUID, val participants: MutableList<Player>) {
}