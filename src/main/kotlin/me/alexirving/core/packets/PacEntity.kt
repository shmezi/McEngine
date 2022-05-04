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
import com.comphenix.protocol.utility.MinecraftVersion
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import me.alexirving.core.utils.AddBack
import me.alexirving.core.utils.pq
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.floor


class PacEntity(id: Int, viewers: MutableList<Player>, val entity: EntityType) : Packet(id, viewers) {

    fun tp(location: Location): PacEntity {
        "Teleporting entity of id$id".pq()
        val pac = buildBasic(PacketType.Play.Server.ENTITY_TELEPORT).apply {
            if (PacketManager.version.isAtLeast(MinecraftVersion.COMBAT_UPDATE))
                doubles.apply {
                    write(0, location.x)
                    write(1, location.y)
                    write(2, location.z)
                }
            else
                integers.apply {
                    write(1, floor(location.x * 32.0).toInt())
                    write(2, floor(location.y * 32.0).toInt())
                    write(3, floor(location.z * 32.0).toInt())
                }


            bytes.apply {
                write(0, (location.yaw * 256.0f / 360.0f).toInt().toByte())
                write(1, (location.pitch * 256.0f / 360.0f).toInt().toByte())
            }
        }
        sendPackets(pac)
        return this
    }

    @AddBack("")
    fun spawn(location: Location): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.SPAWN_ENTITY_LIVING).apply {
            bytes.apply {
                write(0, (((location.yaw * 256F) / 360F)).toInt().toByte())
                write(1, (((location.pitch * 256F) / 360F)).toInt().toByte())
                write(2, 0)
            }

            integers.apply {

                write(1, 1)
                write(2, 0)
                write(3, 0)
                write(4, 0)
                "wow".pq()
            }
            if (PacketManager.version.isAtLeast(MinecraftVersion.COMBAT_UPDATE)) {
                uuiDs.write(0, UUID.randomUUID())
                doubles.apply {
                    write(0, location.x)
                    write(1, location.y)
                    write(2, location.z)
                }
            } else
                integers.apply {
                    write(2, floor(location.x * 32.0).toInt())
                    write(3, floor(location.y * 32.0).toInt())
                    write(4, floor(location.z * 32.0).toInt())
                }
            if (!PacketManager.version.isAtLeast(MinecraftVersion.BEE_UPDATE)) {
                dataWatcherModifier.write(0, WrappedDataWatcher())
            }
        }
        sendPackets(pac)
        return this
    }

    fun setInvisible(): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
        val watcher = WrappedDataWatcher()
        watcher.setObject(0, WrappedDataWatcher.Registry.get(Byte::class.java), 1.toByte())

        pac.watchableCollectionModifier.write(0, watcher.watchableObjects)
//        pac.modifier.write(0, watcher)
//        val a = WrappedDataWatcher().apply {
//            setObject(0, (0x20).toByte())
//        }
//        pac.setMeta("0x20", true)
//        pac.watchableCollectionModifier.write(0, a.toMutableList())
        sendPackets(pac)
        return this
    }

    fun setDisplayName(name: String): PacEntity {
//        val pac = buildBasic(PacketType.Play.Server.ENTITY_METADATA)
//        val a = getMetadata().apply {
//            setObject(2, ChatColor.translateAlternateColorCodes('&', name))
//            setObject(3, 1.toByte())
//        }
//        pac.watchableCollectionModifier.write(0, a.toList())
//        sendPackets(pac)
        return this
    }

    fun kill(): PacEntity {
        val pac = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)
        if (PacketManager.version.isAtLeast(MinecraftVersion.COMBAT_UPDATE))
            pac.intLists.write(0, listOf(id))
        else
            pac.integerArrays.write(0, intArrayOf(id))
        sendPackets(pac)
        return this
    }

    fun setSlot(slot: EnumWrappers.ItemSlot, item: ItemStack): PacEntity {
        val pac = buildBasic(PacketType.Play.Server.ENTITY_EQUIPMENT)
        if (PacketManager.version.isAtLeast(MinecraftVersion.COMBAT_UPDATE))
            pac.slotStackPairLists.write(0, listOf(Pair(slot, item)))
        else {
            //TODO("FIX 1.8 version of this's slot")
            pac.itemModifier.writeSafely(0, item)
            pac.integers.writeSafely(1, slot.ordinal)
        }
        sendPackets(pac)
        return this
    }


}