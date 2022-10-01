/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationCMD.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import me.alexirving.core.EngineManager
import me.alexirving.core.newitem.BaseItem
import me.alexirving.core.utils.pq
import org.bukkit.entity.Player
import java.io.File

@Command("test")
class CMDTest(val m: EngineManager) : BaseCommand() {


    @Permission("engine.test")
    @Default
    fun test(player: Player) {
        val item = BaseItem.fromFile(File(m.df, "pickaxe_of_god.json")).asEngineItem(
            m.item, mutableMapOf(), owner = player.uniqueId
        )
        for (x in 0..15){
            item.upgrade("enchants", "speed").pq()
        }

        player.inventory.addItem(item.getStack())
    }

}