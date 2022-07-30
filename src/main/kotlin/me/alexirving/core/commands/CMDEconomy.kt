package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.points.Points
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command("eco", alias = ["economy", "bal", "balance"])
class CMDEconomy : BaseCommand() {

    @SubCommand("balance", alias = ["bal"])
    @Permission("engine.eco.balance")
    fun bal(sender: CommandSender, points: Points, @Optional player: Player?) {
        val play = if (sender is Player) sender else player ?: throw NullPointerException("ERROR")
        points.getPoints(play.uniqueId) {
            sender.sendMessage("Balance: $it")
        }
    }

    @SubCommand("set")
    @Permission("engine.eco.set")
    fun setBal(sender: Player, points: Points, amount: Double?, @Optional player: Player?) {
        if (player != null)
            points.setPoints(player.uniqueId, amount ?: return)
        else
            points.setPoints(sender.uniqueId, amount ?: return)
    }


    @SubCommand("pay")
    @Permission("engine.eco.pay")
    fun pay(player: Player, points: Points, receiver: Player, amount: Double) {
        points.getPoints(player.uniqueId) {
            if (it >= amount) {
                points.removePoints(player.uniqueId, amount)
                points.addPoints(receiver.uniqueId, amount)
            } else
                player.sendMessage("You do not have enough balance to send!")
        }
    }

    @SubCommand("baltop", alias = ["balancetop"])
    @Permission("engine.eco.balancetop")
    fun balTop(player: Player, points: Points) {
        points.pointTop { users ->
            player.sendMessage(users.map { "${Bukkit.getPlayer(it.identifier)?.displayName}: ${it.points[points.id]}" }
                .joinToString("\n", "Top balances of the server:") { it })
        }

    }

}