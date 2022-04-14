/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationCMD.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.Suggestion
import me.alexirving.core.McEngine
import me.alexirving.core.animation.objects.Animation
import me.alexirving.core.animation.objects.AnimationSession
import me.alexirving.core.animation.utils.Direction
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender

@Command("animation")
class CMDAnimation(val pl: McEngine) : BaseCommand() {


    @Default
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


}