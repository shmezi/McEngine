package me.alexirving.core.channels

import me.alexirving.core.EngineManager
import me.alexirving.core.database.Cacheable
import me.alexirving.core.database.Database
import me.alexirving.core.database.GroupCachedManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*


class ChannelManger(db: Database<Cacheable>, private val m: EngineManager) : Listener,
    GroupCachedManager<ChannelData>(db, ChannelData.default(null)) {

    fun ChannelData.sendMessage(message: String) {
        this@ChannelManger.groups[this.getUUID()]?.forEach { Bukkit.getPlayer(it)?.sendMessage(message) }

    }

    fun setUserChannel(player: Player, channel: UUID) {
        m.user.get(player.uniqueId, true) {
            it.currentChannel = channel
        }
    }

    fun addUserToChannel(player: Player, cId: UUID) {
        val uId = player.uniqueId
        m.user.get(uId, true) { user ->
            get(cId, true) { channel ->
                channel.participants[uId] = channel.default
            }
            user.channels.add(cId)
        }
    }

    fun queryChannel(player: Player, name: String, result: (channelData: ChannelData?) -> Unit) {
        if (isUserCached(player.uniqueId)) {
            var f = false
            m.user.get(player.uniqueId) { user ->
                user.channels.firstOrNull { cId ->
                    get(cId) { if (it.name == name) f = true }
                    f
                }
            }
        } else result(null)
    }

    fun queryPlayerChannels(player: Player, result: (channelData: MutableSet<ChannelData>) -> Unit) {
        if (isUserCached(player.uniqueId))
            result(getAllOfUser(player.uniqueId))

    }

    @EventHandler
    private fun onChat(e: AsyncPlayerChatEvent) {
        if (e.isCancelled) return
        val player = e.player
        val uuid = player.uniqueId
        m.user.get(uuid) { user ->
            get(user.currentChannel?:return@get){
                e.isCancelled = true
                if (it.canWrite(uuid))
                    it.sendMessage("[${it.getPrefix(uuid)}] ${player.displayName}: ${e.message}")
                else
                    player.sendMessage("Sorry you cannot write in chat of name ${it.name}")
            }


        }

    }
}

