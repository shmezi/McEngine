/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * WorldEditHook.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.hooks

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import org.bukkit.Material


class HookWorldEdit : Hook("WorldEdit") {
    private val we: WorldEdit? = WorldEdit.getInstance()

    init {
        we ?: error("World edit was not found!")
    }


    /**
     * Fills in an area of the world using world edit
     * @param region The region (PLEASE MAKE SURE TO INCLUDE A WORLD IN THE FIRST PARAM OF THE WORLD!
     * @param pattern the pattern to use to fill in the area
     */
    fun fill(region: CuboidRegion, pattern: Pattern) {
        we ?: error("World edit was not found!")
        we.newEditSession(region.world).use {
            it.setBlocks(region, pattern)
        }
    }

    fun getDistribution(region: CuboidRegion): MutableMap<Material, Int> {
        we ?: error("World edit was not found!")
        val count = mutableMapOf<Material, Int>()
        we.newEditSession(region.world).use {
            it.getBlockDistribution(region, false)
        }.forEach { count[Material.valueOf(it.id.toBaseBlock().asString.substringAfter(':').uppercase())] = it.amount }
        return count
    }


}