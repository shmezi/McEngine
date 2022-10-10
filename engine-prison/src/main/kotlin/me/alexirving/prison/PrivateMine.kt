package me.alexirving.prison

import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import me.alexirving.core.EngineManager
import org.bukkit.Location
import org.bukkit.entity.Player

class PrivateMine(
    id: String,
    m: EngineManager,
    duration: Long,
    spawn: Location,
    region: CuboidRegion,
    pattern: Pattern,
    var settings: PrisonSettings,
    cmds: List<String>,
    playerReset: List<String>
) : Mine(
    id, m, duration, spawn,
    region, pattern, cmds, playerReset,
) {
    var currentOwner: Player? = null
    var invites = mutableSetOf<Player>()
    fun isOwner(player: Player) = currentOwner == player
    fun isInvited(player: Player) = invites.contains(player)
}