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
import me.alexirving.core.item.instance.EngineItem
import me.alexirving.core.item.instance.InventoryReference
import me.alexirving.core.item.template.BaseItem
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@Command("engine")
class CMDEngine(private val m: EngineManager) : BaseCommand() {
    var b: EngineItem? = null


    @SubCommand("getitem", alias = ["get", "giveItem", "give"])
    @Permission("engine.item.get")
    fun getItem(sender: CommandSender, item: BaseItem, @Optional player: Player?) {
        if (player == null)
            if (sender is Player)
                item.asInstance(m, InventoryReference(sender.inventory, item, UUID.randomUUID())).buildToInventory()
            else
                sender.sendMessage("You must be a player to give yourself an item! try using /engine give <player> <ItemId>")
        else
            item.asInstance(m, InventoryReference(player.inventory, item, UUID.randomUUID())).buildToInventory()

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
        if (m.item.isCustom(player.inventory.itemInMainHand))
            EngineItem.of(m, player.inventory.itemInMainHand, player.inventory)
                ?.forceSetLevel("enchants", effect.id, level)?.updateItem()
    }


}