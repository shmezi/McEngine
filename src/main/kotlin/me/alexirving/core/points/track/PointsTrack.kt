package me.alexirving.core.points.track

import me.alexirving.core.EngineManager
import me.alexirving.core.points.Points
import org.bukkit.Bukkit
import java.util.*

class PointsTrack(id: String, m: EngineManager, private val levels: MutableList<Level>) :
    Points(id, m) {

    override fun addPoints(uuid: UUID, add: Double) {
        super.addPoints(uuid, add)
        getPoints(uuid) { amount ->
            levels[amount.toInt()].cmds.forEach {
                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    it.replace("%uuid%", uuid.toString()).replace("%player%", Bukkit.getPlayer(uuid)?.displayName ?: "")
                )
            }
        }
    }

    fun getLevel(uuid: UUID, async: (level: Level) -> Unit) {
        getPoints(uuid) {
            async(levels[it.toInt()])
        }
    }
}