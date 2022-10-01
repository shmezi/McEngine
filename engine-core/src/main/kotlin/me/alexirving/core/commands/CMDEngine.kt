/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import me.alexirving.core.effects.Effect
import me.alexirving.core.item.ItemManager
import me.alexirving.core.item.instance.EngineItem
import me.alexirving.core.newitem.BaseItem
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command("engine")
class CMDEngine(private val m: EngineManager) : BaseCommand() {
    var b: EngineItem? = null


    @SubCommand("getitem", alias = ["get", "giveItem", "give"])
    @Permission("engine.item.get")
    fun getItem(sender: CommandSender, item: BaseItem, @Optional player: Player?) {
        if (player == null)
            if (sender is Player)
                sender.inventory.addItem(item.asEngineItem(m.item, owner = sender.uniqueId).getStack())
            else
                sender.sendMessage("You must be a player to give yourself an item! try using /engine give <player> <ItemId>")
        else
            player.inventory.addItem(item.asEngineItem(m.item, owner = player.uniqueId).getStack())

    }

    @SubCommand("reload")
    @Permission("engine.core.reload")
    fun reload(sender: CommandSender) {
        m.reload()
    }

    @SubCommand("forceDbUpdate", alias = ["dbUpdate"])
    @Permission("engine.core.dbupdate")
    fun dbUpdate(sender: CommandSender) {
        m.user.update()
    }

    @SubCommand("enchant")
    @Permission("engine.core.enchant")
    fun enchant(player: Player, effect: Effect, level: Int) {
        if (ItemManager.isCustom(player.inventory.itemInMainHand))
            EngineItem.of(m, player.inventory.itemInMainHand, player.inventory)
                ?.forceSetLevel("enchants", effect.id, level)?.updateItem()
    }


}