package me.alexirving.core.commands

import me.alexirving.core.economy.Economy
import me.mattstudios.mf.annotations.*
import me.mattstudios.mf.base.CommandBase
import org.bukkit.entity.Player

@Command("economy")
@Alias("eco")
class CMDEconomy : CommandBase() {
    @SubCommand("balance")
    @Alias("bal")
    @Completion("#ecoIds", "#players")
    fun bal(sender: Player, economy: Economy, @Optional player: Player?) {
        if (player != null)
            economy.getBal(player.uniqueId)
        else
            economy.getBal(sender.uniqueId)
    }

    @SubCommand("set")
    @Completion("#ecoIds", "#empty", "#players")
    fun setBal(sender: Player, economy: Economy, amount: Double?, @Optional player: Player?) {
        if (player != null)
            economy.setBal(player.uniqueId, amount ?: return)
        else
            economy.setBal(sender.uniqueId, amount ?: return)
    }

}