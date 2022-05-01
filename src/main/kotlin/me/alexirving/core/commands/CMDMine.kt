package me.alexirving.core.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import me.alexirving.core.mines.PrivateMine
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

@Command("mine")
class CMDMine(val m: EngineManager) : BaseCommand() {
    @SubCommand("tp")
    @Permission("engine.mine.tp")
    fun tp(player: Player) {
        m.point.getPointTrack("PRESTIGE")?.getLevel(player.uniqueId) {
            m.mine.getMine(it.id)?.join(player)?.tpToSpawn(player)
        }
    }

    @SubCommand("private")
    @Permission("engine.mine.private")
    fun privateMine(player: Player) {
        m.point.getPointTrack("PRESTIGE")?.getLevel(player.uniqueId) {
            m.mine.newPrivateMineSession(it.id, player, { player.sendMessage("Sent you to your private mine!") },
                { player.sendMessage("No private mine for prestige of ${it.id}") })
        }
    }

//    @SubCommand("invite")
//    @Permission("engine.mine.invite")
//    fun invite(player0: Player, player1: Player) {
//        val pm = m.mine.getCurrentMine(player0)
//        if (pm is PrivateMine)
//            if (pm.isOwner(player1) && pm.players.size < 5)
//    }

    @SubCommand("test")
    fun test(player: Player) {
        m.mine.getMine("A")?.breakLayer(Location(Bukkit.getWorld("world")!!, 10.0, 100.0, 10.0), player)
    }
}