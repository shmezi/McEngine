package me.alexirving.core.channels

import me.alexirving.core.EngineManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class ChannelManger(val m: EngineManager) : Listener {
    val channels = mutableMapOf<UUID, Channel>()

    fun join(player: Player) {
//        val profile = m.profile.getProfile(player)
    }
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
//        if (e.isCancelled) return
//        val profile = m.profile.getProfile(e.player)
//        profile.selectedChannel?.participants?.keys?.forEach {
//            it.sendMessage("${profile.selectedChannel!!.participants[e.player]?.prefix} ${e.player.displayName}: ${e.message}")
//        }

    }
}