package me.alexirving.core.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.economy.Economy
import org.bukkit.Bukkit

import org.bukkit.entity.Player

@Command("economy", alias = ["eco"])
class CMDEconomy : BaseCommand() {
    @SubCommand("bal", alias = ["balance"])
    fun bal(sender: Player, economy: Economy, @Optional player: Player?) {
        val m = if (player != null)
            economy.getBal(player.uniqueId)
        else
            economy.getBal(sender.uniqueId)
        sender.sendMessage("Balance: $m")
    }

    @SubCommand("set")
    fun setBal(sender: Player, economy: Economy, amount: Double?, @Optional player: Player?) {
        if (player != null)
            economy.setBal(player.uniqueId, amount ?: return)
        else
            economy.setBal(sender.uniqueId, amount ?: return)
    }

    @SubCommand("baltop")
    fun balTop(player: Player, economy: Economy) {
        economy.m.database.getUsers { users ->
            var b = "Balance top: "
            users.map { user -> "${Bukkit.getPlayer(user.uuid)?.displayName}: ${user.ecos.filter { it.key == economy.id }.values.toList()[0]}" }
                .forEach { b = "$b$it\n" }
            player.sendMessage(b)
        }
    }

}