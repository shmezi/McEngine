package me.alexirving.core.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.economy.Economy

import org.bukkit.entity.Player

@Command("economy", alias = ["eco"])
class CMDEconomy : BaseCommand() {
    @SubCommand("balance", alias = ["bal"])
    fun bal(sender: Player, economy: Economy, @Optional player: Player?) {
        if (player != null)
            economy.getBal(player.uniqueId)
        else
            economy.getBal(sender.uniqueId)
    }

    @SubCommand("set")
    fun setBal(sender: Player, economy: Economy, amount: Double?, @Optional player: Player?) {
        if (player != null)
            economy.setBal(player.uniqueId, amount ?: return)
        else
            economy.setBal(sender.uniqueId, amount ?: return)
    }

}