package me.alexirving.core.gangs

import me.alexirving.core.EngineManager
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.db.Database
import me.alexirving.core.exceptions.ShmeziFuckedUp
import me.alexirving.core.utils.DbManage
import org.bukkit.entity.Player
import java.util.*

class GangManager(override val m: EngineManager, override val db: Database) : DbManage {
    private val cachedGangs = mutableMapOf<UUID, GangData>() //GangID | Gang

    fun getGang(id: UUID, async: (gang: GangData) -> Unit, update: Boolean) {
        if (cachedGangs.containsKey(id))
            async(cachedGangs[id] ?: throw ShmeziFuckedUp("Odd thing happen ig ?"))
        else {
            db.dbGetGang(id, {
                async(it)
                if (update)
                    updateGang(it)
            }, {

            })
        }
    }

    fun getGang(id: UUID, async: (gang: GangData) -> Unit) = getGang(id, async, false)

    fun unloadPlayer(player: Player) {
        m.user.getUser(player) {
            val g = it.settings.gang ?: return@getUser
            if ((cachedGangs[g]?.players?.size ?: 0) <= 1)
                cachedGangs.remove(g)
        }
    }

    fun loadPlayer(player: Player) {
        m.user.getUser(player) { user ->
            val g = user.settings.gang ?: return@getUser
            if (cachedGangs.containsKey(g)) return@getUser
            getGang(g) {
                cachedGangs[g] = it
            }
        }
    }

    fun updateGang(gang: GangData) {
        cachedGangs[gang.getId()] = gang
        db.dbUpdateGang(gang)
    }


    fun registerGang(gang: GangData) {
        m.point.getPoints("default")?.setPoints(gang.getId(), 0.0)

        m.channel.loadChannel(ChannelData.default(gang.owner).apply {
            name = "gang"
        }
        )

        m.channel.loadChannel(ChannelData.default(gang.owner).apply {
            name = "officer"
        }
        )
    }


}