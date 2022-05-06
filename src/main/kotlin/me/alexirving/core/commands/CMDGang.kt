package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import me.alexirving.core.gangs.GangData
import org.bukkit.ChatColor
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
                    if (it.settings.gang != null)
                        m.gang.getGang(it.settings.gang ?: return@getUser) {

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
                m.gang.getGang(user.settings.gang ?: return@getUser) {
                    it.players.remove(player.uniqueId)
                    m.gang.updateGang(it)
                }
            }
        else
            player.sendMessage("Are you sure you want to leave your current gang? if so type /gang leave confirm")
    }

    @SubCommand("public")
    @Permission("engine.gang.public")
    fun public(player: Player) {
        m.user.getUser(player) { user ->
            m.gang.getGang(user.settings.gang ?: return@getUser) {
                it.public = !it.public
                m.gang.updateGang(it)
            }
        }
    }

    @SubCommand("setmotd")
    @Permission("engine.gang.setmotd")
    fun setMotd(player: Player, motd: List<String>) {
        m.user.getUser(player) { user ->
            m.gang.getGang(user.settings.gang ?: return@getUser) {
                it.motd = ChatColor.translateAlternateColorCodes('&', motd.joinToString(" "))
                m.gang.updateGang(it)
            }
        }
    }

    @SubCommand("balance")
    @Permission("engine.gang.balance")
    fun balance(player: Player) {
        m.gang.getGangOfPlayer(player) { gang ->
            val points = m.point.getPoints("default") ?: return@getGangOfPlayer
            gang.balance(points) {
                player.sendMessage("The balance of your gang is $it")
            }
        }
    }


    @SubCommand("delete")
    @Permission("engine.gang.delete")
    fun delete(player: Player, @Optional confirm: String?) {
        if (confirm == "confirm")
            m.gang.getGangOfPlayer(player) {
                m.gang.delete(it.getId())
            }
        else
            player.sendMessage("Are you sure you want to delete your gang? if so type /gang delete confirm")
    }

    @SubCommand("create")
    @Permission("engine.gang.create")
    fun create(player: Player, name: String) {
        m.gang.getGangOfPlayer(player, {
            player.sendMessage("You are already in a gang!")
        }, {
            m.gang.registerGang(GangData.default(m, player.uniqueId, name))
        })

    }
}