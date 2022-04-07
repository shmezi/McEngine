/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationCMD.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Completion
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Optional
import me.mattstudios.mf.base.CommandBase
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender

@Command("animation")
class CMDAnimation(val pl: McEngine) : CommandBase() {


    @Default
    @Completion("#animationIds", "#worlds", "#empty", "#empty", "#empty", "#enum")
    fun cmd(
        player: CommandSender,
        animation: Animation?,
        world: World?,
        x: Double?,
        y: Double?,
        z: Double?,
        @Optional direction: Direction?
    ) {
        AnimationSession(
            pl,
            Bukkit.getOnlinePlayers().toMutableList(),
            Location(world, x ?: return, y ?: return, z ?: return),
            animation ?: return,
            direction ?: Direction.NORTH
        )
    }


//    override fun onTabComplete(
//        sender: CommandSender,
//        command: org.bukkit.command.Command?,
//        alias: String,
//        args: Array<out String>
//    ): MutableList<String> {
//        if (sender is Player) {
//            val player: Player = sender
//
//            return when (args.size) {
//                1 -> pl.am.getAnimationNames().toMutableList()
//                2 -> mutableListOf(player.world.name)
//                3 -> mutableListOf(player.getTargetBlock(setOf(null), 200).x.toString())
//                4 -> mutableListOf(player.getTargetBlock(setOf(null), 200).y.toString())
//                5 -> mutableListOf(player.getTargetBlock(setOf(null), 200).z.toString())
//                6 -> c
//                else -> {
//                    mutableListOf("")
//                }
//            }
//        } else {
//            return mutableListOf("Not a player!")
//        }
//    }
}