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
import me.alexirving.core.item.InstanceItem
import me.alexirving.core.item.InventoryReference
import me.alexirving.core.item.ItemManager
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class Engine(val engine: McEngine) : CommandExecutor {
    var b: InstanceItem? = null
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        if (args.isEmpty()) {
            player.sendMessage("No arguments provided!")
            return true
        }
        val main = player.itemInHand
        val inv = player.inventory
        when (args[0].lowercase()) {
            "help" -> {
                player.sendMessage(
                    """Debug commands:
                    |getnbt - Get's nbt data of an item
                    |getitem - Get's the specified item
                    |list - Lists registered items
                    |wrap - Wraps held item and allows changing of placeholders
                    |getuuid - Gets uuid from held item
                    |""".trimMargin()
                )
            }
            "getnbt" -> {
                val nbt = NBTItem(player.itemInHand)
                player.sendMessage("${nbt.keys}")
            }
            "list" -> {
                player.sendMessage(ItemManager.bases.keys.toString())
            }
            "getitem" -> {
                if (args.size > 1)
                    if (ItemManager.bases.containsKey(args[1])) {
                        val b = ItemManager.bases[args[1]]
                        val v = b?.asInstance(InventoryReference(player.inventory, b, UUID.randomUUID()))
                        if (v == null) {
                            println("Item instance is null".color(Colors.BG_RED))
                            return true
                        }
                        v.buildToInventory()
                        this.b = v
                    } else
                        player.sendMessage("Item with ID of '${args[1]}' was not found!")
                else
                    player.sendMessage("Please provide arguments!")
            }
            "wrap" -> {
                if (args.size > 2) {
                    val a = InstanceItem.of(main, inv)
                    a!!.setLevel(args[1], args[2].toInt())
                    a.updateItem()
                } else
                    player.sendMessage("Please send in format /engine wrap <placeholder> <value> Ex: /engine wrap speed 0")
            }
            "getuuid" -> {
                player.sendMessage(
                    NBTItem(player.itemInHand).getUUID("uuid").toString()
                )
            }

            else -> {
                player.sendMessage("Argument '${args[0]}' is not a valid sub-command!")
            }
        }

        return true
    }

}