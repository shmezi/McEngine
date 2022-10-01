package me.alexirving.core.packets

import org.bukkit.entity.Player

class PacItem(id: Int, viewers: MutableSet<Player>) : Packet(id, viewers) {}