package me.alexirving.prison

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.SubCommand
import me.alexirving.core.EngineManager
import org.bukkit.entity.Player

@Command("mine")
class CMDMine(val m: MineManager) : BaseCommand() {
    @SubCommand("tp")
    @Permission("engine.mine.tp")
    fun tp(player: Player) {
        m.engine.point.getPointTrack("PRESTIGE")?.getLevel(player.uniqueId) {
            m.getMine(it.id)?.join(player)?.tpToSpawn(player)
        }
    }

    @SubCommand("private", alias = ["p"])
    @Permission("engine.mine.private")
    fun privateMine(player: Player) {
        m.engine.point.getPointTrack("PRESTIGE")?.getLevel(player.uniqueId) {
            m.newPrivateMineSession(it.id, player, { player.sendMessage("Sent you to your private mine!") },
                { player.sendMessage("No private mine for prestige of ${it.id}") })
        }
    }

    @SubCommand("invite")
    @Permission("engine.mine.invite")
    fun invite(inviter: Player, invited: Player) {
    }

    @SubCommand("join")
    @Permission("engine.mine.join")
    fun join(player: Player, to: Player) {
    }
}