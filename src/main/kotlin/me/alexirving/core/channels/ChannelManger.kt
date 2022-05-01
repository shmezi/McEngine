package me.alexirving.core.channels

import me.alexirving.core.EngineManager
import me.alexirving.core.db.Database
import me.alexirving.core.utils.pq
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*


class ChannelManger(private val db: Database, private val m: EngineManager) : Listener {
    val channelCache = mutableMapOf<UUID, ChannelData>()
    val online = mutableMapOf<UUID, MutableSet<Player>>()

    fun loadFromPlayer(player: Player) {
        m.user.getUser(player.uniqueId) { user ->
            for (cId in user.channels) {
                db.dbGetChannel(
                    cId,
                    {
                        channelCache[cId] = it
                        online.getOrPut(cId) { mutableSetOf() }.add(player)
                    },
                    { m.user.updateUser(user.apply { channels.remove(cId) }) })

            }
        }

    }

    fun loadChannel(channel: ChannelData): ChannelData {
        db.dbUpdateChannel(channel)
        channelCache[channel.getId()] = channel
        return channel
    }

    fun unloadUser(player: Player) {
        m.user.getUser(player.uniqueId) { user ->
            for (cId in user.channels) {
                val o = online[cId] ?: continue
                o.remove(player)
                if (o.isEmpty())
                    online.remove(cId)
            }
        }
    }

    fun ChannelData.sendMessage(message: String) {
        online[UUID.fromString(this.uuid)]?.forEach { it.sendMessage(message) }
    }

    fun setUserChannel(player: Player, channel: UUID) {
        m.user.getUser(player.uniqueId, true) {
            it.currentChannel = channel
        }
    }

    fun addUserToChannel(player: Player, channel: UUID) {
        val uuid = player.uniqueId
        m.user.getUser(uuid, true) { user ->

            user.currentChannel = channel
            user.channels.add(channel)
            channelCache[channel]?.also {
                if (channelCache[channel]?.participants?.containsKey(uuid) != true) //Check added to make sure user isnt in group (when creating a channel then it can cause issues otherwise.
                    it.participants[uuid] = it.default
            }
            if (player.isOnline)
                online.getOrPut(channel) { mutableSetOf() }.add(player)
        }
    }

    fun queryChannel(player: Player, name: String, result: (channelData: ChannelData?) -> Unit) {
        m.user.getUser(player.uniqueId) { user ->
            result(channelCache.filterKeys { user.channels.contains(it) }
                .values.firstOrNull { it.name == name })
        }
    }

    fun queryPlayerChannels(player: Player, result: (channelData: MutableSet<ChannelData>) -> Unit) {
        m.user.getUser(player.uniqueId) { user ->
            result(
                channelCache.filterKeys { user.channels.contains(it) }.values.toMutableSet()
            )
        }
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        if (e.isCancelled) return

        val player = e.player
        val uuid = player.uniqueId
        m.user.getUser(uuid) {
            val channel = channelCache[it.currentChannel] ?: return@getUser
            e.isCancelled = true
            if (channel.canWrite(uuid))
                channel.sendMessage("[${channel.getPrefix(uuid)}] ${player.displayName}: ${e.message}")
            else
                player.sendMessage("Sorry you cannot write in chat of name ${channel.name}")

        }

    }
}

