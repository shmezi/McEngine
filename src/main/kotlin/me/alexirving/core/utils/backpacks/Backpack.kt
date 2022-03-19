/*
 * (C) 16/03/2022, 23:17 - Alex Irving | All rights reserved
 * Backpack.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.utils.backpacks

import dev.triumphteam.gui.guis.StorageGui
import org.bukkit.entity.Player
import java.util.*

class Backpack(val id: UUID, val gui: StorageGui) {
    fun open(player: Player) = gui.open(player)
    fun close(player: Player) = gui.close(player)
}