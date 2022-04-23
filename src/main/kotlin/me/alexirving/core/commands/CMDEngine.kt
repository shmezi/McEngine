/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import de.tr7zw.changeme.nbtapi.NBTItem
import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import me.alexirving.core.effects.Effect
import me.alexirving.core.item.instance.EngineItem
import me.alexirving.core.item.instance.InventoryReference
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.pq
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@Command("engine")
class CMDEngine(val m: EngineManager) : BaseCommand() {
    var b: EngineItem? = null


    @Default
    @SubCommand("help")
    @Permission("engine.help")
    fun help(sender: CommandSender) {
        sender.sendMessage(
            """Debug commands:
                    |getnbt - Get's nbt data of an item
                    |getitem - Get's the specified item
                    |list - Lists registered items
                    |wrap - Wraps held item and allows changing of placeholders
                    |getuuid - Gets uuid from held item
                    |reload - Reloads the plugin!
                    |""".trimMargin()
        )
    }

    @SubCommand("wrap")
    @Permission("engine.wrap")
    fun wrap(player: Player, section: String, attribute: String, value: Int?) {
        if (player.itemInHand == null) return
        val a = EngineItem.of(m, player.itemInHand, player.inventory)
        a!!.forceSetLevel(section, attribute, value ?: return)
        a.updateItem()
    }

    @SubCommand("up")
    @Permission("engine.upgrade")
    fun up(player: Player, section: String, attribute: String) {
        if (player.itemInHand == null) return
        val a = EngineItem.of(m, player.itemInHand, player.inventory)
        player.sendMessage(a!!.levelUp(section, attribute).name)
        a.updateItem()
    }

    @SubCommand("reload")
    @Permission("engine.reload")
    fun reload(sender: CommandSender) {
        m.reload()
        sender.sendMessage("Reloaded plugin!")

    }

    @SubCommand("getitem")
    @Permission("engine.getitem")
    fun getItem(player: Player, item: BaseItem) {
        val v = item.asInstance(m, InventoryReference(player.inventory, item, UUID.randomUUID()))
        v.buildToInventory()
    }

    @SubCommand("getnbt")
    @Permission("engine.getnbt")
    fun getNbt(player: Player, @Optional attribute: String?) {
        if (player.itemInHand == null) return
        if (attribute == null)
            player.sendMessage("nbt data: '${NBTItem(player.itemInHand).keys}'")
        else
            player.sendMessage("nbt data: '${NBTItem(player.itemInHand).getObject("attributes", Map::class.java)}'")

    }

    @SubCommand("getuuid")
    @Permission("engine.getuuid")
    fun getUUID(player: Player) {
        if (player.itemInHand == null) return
        player.sendMessage("uuid of item: '${NBTItem(player.itemInHand).getUUID("uuid")}'")
    }

    @SubCommand("effect")
    @Permission("engine.effect")
    fun effect(player: Player, effect: Effect?, level: Int?) {
        effect.pq("effect")
        m.user.getUser(player.uniqueId) {
            it.activeEffects[effect ?: return@getUser] = level ?: 0
        }

    }


    @SubCommand("test")
    @Permission("engine.test")
    fun test(player: Player, mineId: String) {
        m.mines.getMine(mineId)?.resetMine()

    }

    @SubCommand("test2")
    @Permission("engine.test")
    fun test2(player: Player, mineId: String) {
        m.mines.newPrivateMineSession(mineId, player, {
            it.tpToSpawn(player)
            player.sendMessage("You are being sent to your private area!")
        }, {
            player.sendMessage("Issue happen!")
        })

    }

    @SubCommand("tspawn")
    @Permission("engine.test")
    fun test3(player: Player, mineId: String) {
        m.mines.getMine(mineId)?.tpToSpawn(player)
    }

    @SubCommand("updateDb")
    @Permission("engine.updateDb")
    fun updateDb(sender: CommandSender) {
        m.user.updateDb()
    }
}