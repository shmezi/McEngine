package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import me.alexirving.core.channels.Group
import org.bukkit.entity.Player

@Command("gang")
class CMDGang(val m: EngineManager) : BaseCommand() {
    @SubCommand("join")
    @Permission("engine.gang.join")
    fun join(player: Player) {
    }

    @SubCommand("invite")
    @Permission("engine.gang.invite")
    fun invite(inviter: Player, invited: Player) {
    }

    @SubCommand("leave")
    @Permission("engine.gang.leave")
    fun leave(player: Player) {

    }

    @SubCommand("public")
    @Permission("engine.gang.public")
    fun public(player: Player) {
    }

    @SubCommand("setmotd")
    @Permission("engine.gang.setmotd")
    fun setMotd(player: Player, motd: String) {
    }

    @SubCommand("confirm")
    @Permission("engine.gang.setprefix")
    fun setPrefix(player: Player,newPrefix:String) {
    }

    @SubCommand("group")
    @Permission("engine.gang.group")
    fun group(player: Player, group: Group) {
    }

    @SubCommand("delete")
    @Permission("engine.gang.delete")
    fun delete(player: Player) {
    }

    @SubCommand("confirm")
    @Permission("engine.gang.confirm")
    fun confirm(player: Player) {
    }
}