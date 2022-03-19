/*
 * (C) 16/03/2022, 23:18 - Alex Irving | All rights reserved
 * BackpackManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.utils.backpacks

import com.google.gson.Gson
import dev.triumphteam.gui.guis.StorageGui
import org.bukkit.entity.Player
import java.io.File
import java.io.FileReader

class BackpackManager(val dir: File) {
    val loaded = mutableMapOf<Player, MutableList<StorageGui>>()
    val g = Gson()
    fun loadPacks(player: Player) {
        val l = File(dir, player.uniqueId.toString())
        if (!l.exists()) {
            l.mkdirs()
            return
        }
        for (f in l.listFiles() ?: return)
            if (loaded.contains(player))
                loaded[player]?.add(
                    g.fromJson(FileReader(f), StorageGui::class.java)
                )
            else loaded[player] = mutableListOf(g.fromJson(FileReader(f), StorageGui::class.java))
    }

    fun unloadPacks() {}
}