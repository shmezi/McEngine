/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.ItemManager
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Tool : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        when (args[0].lowercase()) {

            "getnbt" -> {
                player.sendMessage(NBTItem(player.itemInHand).toString())
            }
            "setnbt" -> {
                NBTItem(player.itemInHand, true).setString(args[1], args[2])
            }
            "getitem" -> {
                val i = ItemStack(Material.STONE)
                player.inventory.addItem(i)

                val v = ItemManager.bases["SuperPick"]?.asInstance()
                if (v == null) {
                    println("Item instance is null")
                    return true
                }
                player.inventory.addItem(v.getReference())
                v.buildFromTemplate(mutableMapOf())
                player.updateInventory()
            }
            "c" -> {

            }
            "b" -> {
                player.sendMessage(
                    "Block selected is facing ${
                        player.getTargetBlock(setOf(Material.AIR), 10)
                    }"
                )
            }
        }

        return true
    }
}