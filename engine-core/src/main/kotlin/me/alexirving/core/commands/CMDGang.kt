package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

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
        m.user.get(invited.uniqueId) { iUser ->
            if (iUser.settings.gang == null)
                m.user.get(inviter.uniqueId) {
                    if (it.settings.gang != null)
                        m.gang.get(it.settings.gang ?: return@get) {
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
            m.user.get(player.uniqueId) { user ->
                m.gang.get(user.settings.gang ?: return@get, true) {
                    it.players.remove(player.uniqueId)

                }
            }
        else
            player.sendMessage("Are you sure you want to leave your current gang? if so type /gang leave confirm")
    }

    @SubCommand("public")
    @Permission("engine.gang.public")
    fun public(player: Player) {
        m.user.get(player.uniqueId, true) { user ->
            m.gang.get(user.settings.gang ?: return@get) {
                it.public = !it.public
            }
        }
    }

    @SubCommand("setmotd")
    @Permission("engine.gang.setmotd")
    fun setMotd(player: Player, motd: List<String>) {
        m.user.get(player.uniqueId, true) { user ->
            m.gang.get(user.settings.gang ?: return@get) {
                it.motd = ChatColor.translateAlternateColorCodes('&', motd.joinToString(" "))
            }
        }
    }

    @SubCommand("balance")
    @Permission("engine.gang.balance")
    fun balance(player: Player) {
    }


    @SubCommand("delete")
    @Permission("engine.gang.delete")
    fun delete(player: Player, @Optional confirm: String?) {
        if (confirm == "confirm")
            m.user.get(player.uniqueId, true) { user ->
                m.gang.delete(user.settings.gang ?: return@get)
            }
        else
            player.sendMessage("Are you sure you want to delete your gang? if so type /gang delete confirm")
    }

    @SubCommand("create")
    @Permission("engine.gang.create")
    fun create(player: Player, name: String) {
        m.user.doesExist(player.uniqueId) {
            if (it)
                m.gang.get(
                    UUID.randomUUID()
                ){
                    player.sendMessage("You are already in a gang!")
                }
        }

    }
}