/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Packet.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.packets

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.manager.player.PlayerManager
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import org.bukkit.entity.Player

abstract class Packet(val id: Int, val viewers: MutableSet<Player>) {
    private val lib: ProtocolManager = ProtocolLibrary.getProtocolManager()

    fun buildBasic(packet: PacketType): PacketContainer {
        val c = PacketContainer(packet)
        c.integers.writeSafely(0, id) //Setting the id of the entity
        return c
    }

    fun send(pac: PacketContainer) {
        viewers.forEach { lib.sendServerPacket(it, pac) }
    }

    private val api: PlayerManager = PacketEvents.getAPI().playerManager
    fun <T : PacketWrapper<*>> send(pac: PacketWrapper<T>) {
        viewers.forEach { api.sendPacket(it, pac) }
    }


}