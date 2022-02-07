package me.alexirving.core.animation.packets
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

class AniEntity(private val id: Int, val viewers: MutableList<Player>, val type: EntityType) {


    val proto = ProtocolLibrary.getProtocolManager()
    private fun buildBasic(packet: PacketType): PacketContainer {
        val c = PacketContainer(packet)
        c.integers.write(0, id) //Setting the id of the entity

        return c
    }

    fun tp(location: Location): AniEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_TELEPORT)
        pac.integers.write(1, floor(location.x * 32.0).toInt())
        pac.integers.write(2, floor(location.y * 32.0).toInt())
        pac.integers.write(3, floor(location.z * 32.0).toInt())
        pac.bytes.write(0, (location.yaw * 256.0f / 360.0f).toInt().toByte())
        pac.bytes.write(1, (location.pitch * 256.0f / 360.0f).toInt().toByte())
        sendPackets(pac)
        return this
    }

    fun spawn(location: Location): AniEntity {
        val pac = buildBasic(PacketType.Play.Server.SPAWN_ENTITY_LIVING)
        pac.integers.write(1, type.typeId.toInt())
        pac.integers.write(2, floor(location.x * 32.0).toInt())
        pac.integers.write(3, floor(location.y * 32.0).toInt())
        pac.integers.write(4, floor(location.z * 32.0).toInt())
        pac.integers.write(5, (location.x * 8000.0).toInt())
        pac.integers.write(6, (location.y * 8000.0).toInt())
        pac.integers.write(7, (location.z * 8000.0).toInt())
        pac.bytes.write(0, (((location.yaw * 256F) / 360F)).toInt().toByte())
        pac.bytes.write(1, (((location.pitch * 256F) / 360F)).toInt().toByte())
        pac.bytes.write(2, (((location.pitch * 256F) / 360F)).toInt().toByte())
        pac.dataWatcherModifier.write(0, WrappedDataWatcher())
        sendPackets(pac)
        return this
    }

    private fun sendPackets(pac: PacketContainer) {
        for (viewer in viewers) {
            proto.sendServerPacket(viewer, pac)
        }
    }

    fun setInvisible(): AniEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
        val a = getMetadata().apply {
            setObject(0, (0x20).toByte())
        }
        pac.watchableCollectionModifier.write(0, a.toMutableList())
        sendPackets(pac)
        return this
    }

    fun setDisplayName(name: String): AniEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
        val a = getMetadata().apply {
            setObject(2, ChatColor.translateAlternateColorCodes('&', name))
            setObject(3, 1.toByte())
        }
        pac.watchableCollectionModifier.write(0, a.toList())
        sendPackets(pac)
        return this
    }

    fun kill(): AniEntity {
        val pac = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)
        pac.integerArrays.write(0, intArrayOf(id))
        sendPackets(pac)
        return this
    }

    fun setSlot(slot: Int, item: ItemStack): AniEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_EQUIPMENT)
        pac.itemModifier.write(0, item)
        pac.integers.write(1, slot)
        sendPackets(pac)
        return this
    }

    private fun getMetadata(): WrappedDataWatcher = WrappedDataWatcher()
}