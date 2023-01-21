package me.alexirving.prison.mines

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.world.block.BlockTypes
import me.alexirving.core.EngineManager
import me.alexirving.core.exceptions.NotFoundException
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

open class Mine(
    val id: String,
    private val m: EngineManager,
    val duration: Long,
    var spawn: Location,
    val region: CuboidRegion,
    val pattern: Pattern,
    val reset: List<String>,
    val playerReset: List<String>
) {
    val players: MutableSet<Player> = mutableSetOf()

    fun join(player: Player): Mine {
        players.add(player)
        return this
    }

    fun resetMine(): Mine {
        m.weHook.fill(region, pattern)
        reset.forEach { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), it) }
        players.forEach { player ->
            playerReset.forEach {
                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    it.replace("%player%", player.displayName).replace("%uuid%", player.uniqueId.toString())
                )
            }
            if (region.contains(BukkitAdapter.asBlockVector(player.location))) tpToSpawn(player)
        }
        return this
    }

    fun breakLayer(loc: Location, player: Player?) {
        val r = CuboidRegion(region.world, region.pos1.withY(loc.y.toInt()), region.pos2.withY(loc.y.toInt()))
        m.weHook.getDistribution(r).forEach {
            player?.inventory?.addItem(ItemStack(it.key, it.value))
        }

        m.weHook.fill(
            r,
            BlockTypes.AIR?.defaultState ?: throw NotFoundException("Sorry, air id is not found!")
        )

    }

    fun breakMine(player: Player?) {
        m.weHook.getDistribution(region).forEach {
            player?.inventory?.addItem(ItemStack(it.key, it.value))
        }
        m.weHook.fill(
            region,
            BlockTypes.AIR?.defaultState ?: throw NotFoundException("Sorry, air id is not found!")
        )

    }

    fun tpToSpawn(player: Player): Mine {
        player.teleport(spawn)
        return this
    }

}