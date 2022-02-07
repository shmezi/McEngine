package me.alexirving.core.animation.packets
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.packets.AniEntity
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * PacketManager is a class that manages the packets that are sent to the client.
 */
class PacketManager {
    private val packetMap = mutableMapOf<Int, AniEntity>() //A map to reference the packets by their id
    var idCounter = 0
    fun spawn(type: EntityType, viewers: MutableList<Player>, location: Location): Int {
        packetMap[idCounter] = AniEntity(idCounter, viewers, type).spawn(location.clone().add(0.5, -1.2, 0.5))
        setInvisible(idCounter)
        return idCounter++
    }

    fun tp(id: Int, location: Location) {
        packetMap[id]?.tp(location)
    }

    fun kill(id: Int) {
        packetMap[id]?.kill()
    }

    fun addViewers(id: Int, viewers: MutableList<Player>) {
        packetMap[id]?.viewers?.addAll(viewers)
    }


    fun setInvisible(id: Int) {
        packetMap[id]?.setInvisible()
    }

    fun setDisplayName(id: Int, displayName: String) {
        packetMap[id]?.setDisplayName(displayName)
    }

    fun setItem(id: Int, slot: Int, item: ItemStack) {
        packetMap[id]?.setSlot(slot, item)
    }
}