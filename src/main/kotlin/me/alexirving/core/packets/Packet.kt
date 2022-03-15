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
import com.comphenix.protocol.utility.MinecraftVersion
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.entity.Player

abstract class Packet(val id: Int, val viewers: MutableList<Player>) {
    private val lib: ProtocolManager = ProtocolLibrary.getProtocolManager()

    fun buildBasic(packet: PacketType): PacketContainer {
        val c = PacketContainer(packet)
        c.integers.writeSafely(0, id) //Setting the id of the entity
        return c
    }

    fun sendPackets(pac: PacketContainer) {
        for (viewer in viewers) {
            lib.sendServerPacket(viewer, pac)
        }
    }

    fun getMetadata(): WrappedDataWatcher = WrappedDataWatcher()
}