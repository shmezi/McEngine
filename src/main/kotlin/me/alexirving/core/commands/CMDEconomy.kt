package me.alexirving.core.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.economy.Economy
import me.alexirving.core.utils.nB
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command("economy", alias = ["eco"])
class CMDEconomy : BaseCommand() {
    @SubCommand("bal", alias = ["balance"])
    fun bal(sender: CommandSender, economy: Economy, @Optional player: Player?) {
        val play = if (sender is Player) sender else player ?: throw NullPointerException("ERROR")
        economy.getBal(play.uniqueId) {
            sender.sendMessage("Balance: $it")
        }

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
        economy.balTop { users ->
            val baltop = StringBuilder("Top balances of the server:")
            users.map { "${Bukkit.getPlayer(it.uuid)?.displayName}: ${it.ecos[economy.id]}" }
                .forEachIndexed { i, it -> baltop.append("\n#${i.nB(1)} $it") }
            player.sendMessage(baltop.toString())
        }

    }


}