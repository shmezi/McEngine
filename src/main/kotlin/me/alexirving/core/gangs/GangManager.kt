package me.alexirving.core.gangs

import me.alexirving.core.EngineManager
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.db.Database
import me.alexirving.core.exceptions.ShmeziFuckedUp
import org.bukkit.entity.Player
import java.util.*

class GangManager(private val m: EngineManager, private val db: Database) {
    private val cachedGangs = mutableMapOf<UUID, GangData>() //GangID | Gang
    private val invited = mutableMapOf<UUID, MutableSet<Player>>()
    fun getGang(id: UUID, update: Boolean, async: (gang: GangData) -> Unit) {
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

    fun isInvited(player: Player, id: UUID) = invited[id]?.contains(player) ?: false
    fun invite(player: Player, id: UUID) = invited.getOrPut(id) { mutableSetOf() }.add(player)
    fun unInvite(player: Player, id: UUID) = invited[id]?.remove(player)
    fun delete(id: UUID) {
        getGang(id) { gang ->
            gang.players.forEach { uuid ->
                m.user.getUser(uuid, true) {
                    it.settings.gang = null
                }
            }
            cachedGangs.remove(id)
            db.dbDeleteGang(id)
        }
    }

    fun getGang(id: UUID, async: (gang: GangData) -> Unit) = getGang(id, false, async)

    fun unloadPlayer(player: Player) {
        m.user.getUser(player) {
            val g = it.settings.gang ?: return@getUser
            if ((cachedGangs[g]?.players?.size ?: 0) <= 1)
                cachedGangs.remove(g)
            else cachedGangs[g]?.players?.remove(player.uniqueId)
        }
    }

    fun loadPlayer(player: Player) {
        m.user.getUser(player) { user ->
            val g = user.settings.gang ?: return@getUser
            if (cachedGangs.containsKey(g)) return@getUser
            getGang(g) {
                cachedGangs[g] = it
                player.sendMessage(it.motd)
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
            label = "g"
        }
        )

        m.channel.loadChannel(ChannelData.default(gang.owner).apply {
            name = "officer"
            label = "o"
        }
        )
    }


}