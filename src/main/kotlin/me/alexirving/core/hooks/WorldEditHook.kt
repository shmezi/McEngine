/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * WorldEditHook.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.hooks

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.world.block.BlockTypes
import org.bukkit.Bukkit


class WorldEditHook {
    val we = WorldEdit.getInstance()
    fun fill(region: CuboidRegion) {
        val pattern = RandomPattern()

        val b = BlockTypes.STONE!!.defaultState
        pattern.add(b, 100.0)

        we!!.newEditSession(region.world ?: BukkitAdapter.adapt(Bukkit.getWorld("world")))!!.setBlocks(

            region, pattern
        )
    }


}