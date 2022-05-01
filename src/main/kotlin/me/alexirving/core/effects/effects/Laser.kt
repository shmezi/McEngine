package me.alexirving.core.effects.effects

import me.alexirving.core.EngineManager
import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.block.BlockBreakEvent

class Laser(val m: EngineManager) : Effect("laser", Intent.MINE) {

    override fun onMine(e: BlockBreakEvent, level: Int) {
        m.mine.isInPlayerMine(e.player, e.block.location) { inMine ->
            if (!inMine) return@isInPlayerMine
            val loc = e.block.location

            e.player.sendMessage("MINED!")
            if (e.player.location.pitch > 90 || e.player.location.pitch < 90) {
                loc.clone().add(0.0, 0.0, 1.0).block.type = Material.AIR
                loc.clone().subtract(0.0, 0.0, 1.0).block.type = Material.AIR
                loc.clone().subtract(1.0, 0.0, 0.0).block.type = Material.AIR
                loc.clone().add(1.0, 0.0, 0.0).block.type = Material.AIR
                return@isInPlayerMine
            }
            when (e.player.facing) {
                BlockFace.EAST, BlockFace.WEST -> {


                    loc.clone().add(0.0, 1.0, 0.0).block.type = Material.AIR
                    loc.clone().subtract(0.0, 1.0, 0.0).block.type = Material.AIR
                    loc.clone().subtract(0.0, 0.0, 1.0).block.type = Material.AIR
                    loc.clone().add(0.0, 0.0, 1.0).block.type = Material.AIR

                }
                BlockFace.NORTH, BlockFace.SOUTH -> {
                    loc.clone().add(0.0, 1.0, 0.0).block.type = Material.AIR
                    loc.clone().subtract(0.0, 1.0, 0.0).block.type = Material.AIR
                    loc.clone().subtract(1.0, 0.0, 0.0).block.type = Material.AIR
                    loc.clone().add(1.0, 0.0, 0.0).block.type = Material.AIR
                }
            }
        }
    }
}