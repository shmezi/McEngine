package me.alexirving.core.animation.cmds
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.AnimationManager
import me.alexirving.core.animation.AnimationSession
import me.alexirving.core.animation.Direction
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class AnimationCMD(val pl: McEngine, val am: AnimationManager) : TabExecutor {
    val c = mutableListOf("North", "South", "East", "West")

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        if (args.size > 5) {
            val tani = am.getAnimation(args[0])
            if (tani != null) {
                if (Bukkit.getWorld(args[1]) != null) {
                    if (doubleListCheck(arrayOf(args[2], args[3], args[4]))) {
                        AnimationSession(
                            pl,
                            Bukkit.getOnlinePlayers().toMutableList(),
                            Location(
                                Bukkit.getWorld(args[1]),
                                args[2].toDouble(),
                                args[3].toDouble(),
                                args[4].toDouble()
                            ),
                            tani,
                            Direction.valueOf(args[5].uppercase())
                        ).start()
                    } else
                        sender.sendMessage("Invalid doubles provided for cords.")
                } else {
                    sender.sendMessage("World not found")
                }
            } else {
                sender.sendMessage("Sorry, animation \"${args[0]}\" does not exist!")
            }
        } else
            sender.sendMessage("Sorry, you did not provide enough arguments!")
        return true
    }

    fun doubleListCheck(array: Array<String>): Boolean {
        for (value in array) {
            if (value.toDoubleOrNull() == null)
                return false
        }
        return true
    }


    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (sender is Player) {
            val player: Player = sender

            return when (args.size) {
                1 -> am.getAnimationNames().toMutableList()
                2 -> mutableListOf(player.world.name)
                3 -> mutableListOf(player.getTargetBlock(setOf(null), 200).x.toString())
                4 -> mutableListOf(player.getTargetBlock(setOf(null), 200).y.toString())
                5 -> mutableListOf(player.getTargetBlock(setOf(null), 200).z.toString())
                6 -> c
                else -> {
                    mutableListOf("")
                }
            }
        } else {
            return mutableListOf("Not a player!")
        }
    }
}