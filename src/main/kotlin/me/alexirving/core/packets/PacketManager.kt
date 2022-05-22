/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * PacketManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.packets

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.utility.MinecraftVersion
import com.comphenix.protocol.wrappers.EnumWrappers
import me.alexirving.core.utils.AddBack
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * PacketManager is a class that manages the packets that are sent to the client.
 */
class PacketManager {
    private val packetMap = mutableMapOf<Int, Packet>() //A map to reference the packets by their id

    companion object {
        val version: MinecraftVersion = ProtocolLibrary.getProtocolManager().minecraftVersion
    }

    var idCounter = 0
    fun spawn(type: EntityType, viewers: MutableSet<Player>, location: Location): Int {
        packetMap[idCounter] = PacEntity(idCounter, viewers, type).spawn(location.clone().add(0.5, -1.2, 0.5))
        setInvisible(idCounter)
        return idCounter++

    }


    fun tp(id: Int, location: Location) {
        (packetMap[id] as PacEntity? ?: return).tp(location)
    }

    fun kill(id: Int) {
        (packetMap[id] as PacEntity? ?: return).kill()
    }

    fun addViewers(id: Int, viewers: MutableList<Player>) {
        (packetMap[id] as PacEntity? ?: return).viewers.addAll(viewers)
    }


    fun setInvisible(id: Int) {
        (packetMap[id] as PacEntity? ?: return).setInvisible(true)
    }

    fun setDisplayName(id: Int, displayName: String) {
        (packetMap[id] as PacEntity? ?: return).setDisplayName(displayName)
    }

    @AddBack("Basically just make it allow different slots again later")
    fun setItem(id: Int, slot: Int, item: ItemStack) {

        (packetMap[id] as PacEntity? ?: return).setSlot(EnumWrappers.ItemSlot.HEAD, item)
    }


}