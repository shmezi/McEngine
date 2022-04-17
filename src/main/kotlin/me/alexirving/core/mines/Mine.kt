package me.alexirving.core.mines

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import me.alexirving.core.EngineManager
import org.bukkit.Location
import org.bukkit.entity.Player

open class Mine(
    val id: String,
    val m: EngineManager,
    val duration: Long,
    var spawn: Location,
    val region: CuboidRegion,
    val pattern: Pattern
) {
    private val players: MutableSet<Player> = mutableSetOf()

    fun join(player: Player) {
        players.add(player)
    }

    fun resetMine() {
        m.weHook.fill(region, pattern)
        players.forEach { if (region.contains(BukkitAdapter.asBlockVector(it.location))) tpToSpawn(it) }
    }

    fun tpToSpawn(player: Player) = player.teleport(spawn)

}