/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * PacEntity.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.packets

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

class PacEntity(id: Int, viewers: MutableList<Player>, val type: EntityType) : Packet(id, viewers) {

    fun tp(location: Location): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_TELEPORT)
        pac.integers.writeSafely(1, floor(location.x * 32.0).toInt())
        pac.integers.writeSafely(2, floor(location.y * 32.0).toInt())
        pac.integers.writeSafely(3, floor(location.z * 32.0).toInt())
        pac.bytes.writeSafely(0, (location.yaw * 256.0f / 360.0f).toInt().toByte())
        pac.bytes.writeSafely(1, (location.pitch * 256.0f / 360.0f).toInt().toByte())
        sendPackets(pac)
        return this
    }

    fun spawn(location: Location): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.SPAWN_ENTITY_LIVING)
        pac.integers.writeSafely(1, type.typeId.toInt())
        pac.integers.writeSafely(2, floor(location.x * 32.0).toInt())
        pac.integers.writeSafely(3, floor(location.y * 32.0).toInt())
        pac.integers.writeSafely(4, floor(location.z * 32.0).toInt())
        pac.integers.writeSafely(5, (location.x * 8000.0).toInt())
        pac.integers.writeSafely(6, (location.y * 8000.0).toInt())
        pac.integers.writeSafely(7, (location.z * 8000.0).toInt())
        pac.bytes.writeSafely(0, (((location.yaw * 256F) / 360F)).toInt().toByte())
        pac.bytes.writeSafely(1, (((location.pitch * 256F) / 360F)).toInt().toByte())
        pac.bytes.writeSafely(2, (((location.pitch * 256F) / 360F)).toInt().toByte())
        pac.dataWatcherModifier.writeSafely(0, WrappedDataWatcher())
        sendPackets(pac)
        return this
    }

    fun setInvisible(): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
        val a = getMetadata().apply {
            setObject(0, (0x20).toByte())
        }
        pac.watchableCollectionModifier.writeSafely(0, a.toMutableList())
        sendPackets(pac)
        return this
    }

    fun setDisplayName(name: String): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
        val a = getMetadata().apply {
            setObject(2, ChatColor.translateAlternateColorCodes('&', name))
            setObject(3, 1.toByte())
        }
        pac.watchableCollectionModifier.writeSafely(0, a.toList())
        sendPackets(pac)
        return this
    }

    fun kill(): PacEntity {
        val pac = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)
        pac.integerArrays.writeSafely(0, intArrayOf(id))
        sendPackets(pac)
        return this
    }

    fun setSlot(slot: Int, item: ItemStack): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_EQUIPMENT)
        pac.itemModifier.writeSafely(0, item)
        pac.integers.writeSafely(1, slot)
        sendPackets(pac)
        return this
    }


}