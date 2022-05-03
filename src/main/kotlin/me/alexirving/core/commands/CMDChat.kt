/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * AnimationCMD.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import me.alexirving.core.EngineManager
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.utils.manager.CmdManage
import org.bukkit.entity.Player

@Command("chat")
class CMDChat(override val m: EngineManager) : CmdManage() {


    @Default
    @Permission("engine.chat.select")
    fun select(player: Player, chat: ChannelData) {

    }

}