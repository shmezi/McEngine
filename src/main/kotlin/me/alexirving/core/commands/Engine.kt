/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Tool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.regions.CuboidRegion
import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.McEngine
import me.alexirving.core.econemy.EcoManager
import me.alexirving.core.hooks.WorldEditHook
import me.alexirving.core.item.InventoryReference
import me.alexirving.core.item.ItemManager
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.getPickaxe
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Engine(val engine: McEngine) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player!")
            return true
        }
        val player: Player = sender
        when (args[0].lowercase()) {
            "test" -> {
                player.inventory.addItem(getPickaxe(engine.config, player))
            }
            "getnbt" -> {
                player.sendMessage(NBTItem(player.itemInHand).toString())
            }
            "setnbt" -> {
                NBTItem(player.itemInHand, true).setString(args[1], args[2])
            }
            "getitem" -> {
                val b = ItemManager.bases["SuperPick"]
                val v = b?.asInstance(InventoryReference(player.inventory, b, UUID.randomUUID()))
                if (v == null) {
                    println("Item instance is null".color(Colors.BG_RED))
                    return true
                }
//                v.buildFromTemplate(mutableMapOf())
                v.buildToInventory()
                player.updateInventory()
            }
            "setbal" -> {
                EcoManager.getEco(args[1])?.setBal(player.uniqueId, args[2].toDoubleOrNull() ?: 0.0)
            }
            "getbal" -> {
                player.sendMessage("Your balance is ${EcoManager.getEco(args[1])?.getBal(player.uniqueId)}")
            }
            "getbp" -> {
                val a = NBTItem(ItemStack(Material.CHEST))
                a.setUUID("id", UUID.randomUUID())
                player.inventory.addItem(a.item)
            }
            "we" -> {
                val a = WorldEditHook()
                val region = CuboidRegion.fromCenter(BukkitAdapter.adapt(player.location).toVector().toBlockPoint(), 20)
                a.fill(region)
            }
            "getpickaxe" -> {

            }
            "b" -> {
                player.sendMessage(
                    "Block selected is facing ${
                        player.getTargetBlock(setOf(Material.AIR), 10)
                    }"
                )
            }
            else -> {
                player.sendMessage("Argument \"${args[0]}\"!")
            }
        }

        return true
    }

}