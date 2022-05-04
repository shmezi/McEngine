package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
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
        if (!invited.isOnline) return
        m.user.getUser(invited) { invited ->
            if (invited.settings.gang == null)
                m.user.getUser(inviter) {
                if (it.settings.gang !=null)
                    m.gang.getGang(it.settings.gang?:return@getUser){

                    }

                }
            else
                inviter.sendMessage("player is already in gang")
        }

    }

    @SubCommand("leave")
    @Permission("engine.gang.leave")
    fun leave(player: Player, @Optional confirm: String?) {
        if (confirm == "confirm")
            m.user.getUser(player) { user ->
                m.gang.getGang(user.settings.gang ?: return@getUser, true) {
                    it.players.remove(player.uniqueId)
                }
            }
        else
            player.sendMessage("Are you sure you want to leave your current gang? if so type /gang leave confirm")
    }

    @SubCommand("public")
    @Permission("engine.gang.public")
    fun public(player: Player) {
        m.user.getUser(player) { user ->
            m.gang.getGang(user.settings.gang ?: return@getUser, true) {
                it.public = !it.public
            }
        }
    }

    @SubCommand("setmotd")
    @Permission("engine.gang.setmotd")
    fun setMotd(player: Player, motd: String) {
        m.user.getUser(player) { user ->
            m.gang.getGang(user.settings.gang ?: return@getUser, true) {
                it.motd = motd
            }
        }
    }


    @SubCommand("delete")
    @Permission("engine.gang.delete")
    fun delete(player: Player, @Optional confirm: String?) {
        if (confirm == "confirm")
            m.user.getUser(player) { user ->
                m.gang.delete(user.settings.gang ?: return@getUser)
            }
        else
            player.sendMessage("Are you sure you want to delete your gang? if so type /gang delete confirm")
    }
}