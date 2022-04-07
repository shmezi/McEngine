/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.McEngine
import me.alexirving.core.item.instance.InventoryReference
import me.alexirving.core.item.instance.ItemInstance
import me.alexirving.core.item.template.BaseItem
import me.mattstudios.mf.annotations.*
import me.mattstudios.mf.annotations.Optional
import me.mattstudios.mf.base.CommandBase
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

@Command("engine")
class CMDEngine(val engine: McEngine) : CommandBase() {
    var b: ItemInstance? = null


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
        val a = ItemInstance.of(player.itemInHand, player.inventory)
        a!!.setLevel(section, attribute, value ?: return)
        a.updateItem()
    }

    @SubCommand("reload")
    @Permission("engine.reload")
    fun reload(sender: CommandSender) {
        engine.reload()
        sender.sendMessage("Reloaded plugin!")

    }

    @SubCommand("getitem")
    @Completion("#baseIds")
    @Permission("engine.getitem")
    fun getItem(player: Player, item: BaseItem) {
        val v = item.asInstance(InventoryReference(player.inventory, item, UUID.randomUUID()))
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


    @SubCommand("test")
    fun test(player: Player) {
        val item = ItemStack(Material.REDSTONE_BLOCK)
        val m = mutableMapOf<String, Int>()
        m["s"] = 0
        m["y"] = 20

        val nbt = NBTItem(item)
        nbt.setObject("test", m)
        nbt.mergeNBT(item)
        player.inventory.addItem(item)
    }

    @SubCommand("test1")
    fun t2(player: Player) {
        val l = NBTItem(player.itemInHand).getObject("test", Map::class.java).toMutableMap()
        player.sendMessage("${l.keys}")
    }

}