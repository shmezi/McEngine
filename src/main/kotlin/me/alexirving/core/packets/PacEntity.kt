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
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import me.alexirving.core.utils.AddBack
import me.alexirving.core.utils.pq
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.floor


class PacEntity(id: Int, viewers: MutableList<Player>, val entity: EntityType) :
    Packet(id, viewers) {
    val options = mutableMapOf<PacEntityOption, Boolean>()

    fun tp(location: Location): PacEntity {
        "Teleporting entity of id$id to location $location".pq()
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
        send(pac)
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
        send(pac)
        return this
    }

    fun setInvisible(boolean: Boolean): PacEntity {
        options[PacEntityOption.INVISIBLE] = boolean

        send(WrapperPlayServerEntityMetadata(id, listOf(EntityData(0, EntityDataTypes.BYTE, 0x20.toByte()))))
        return this
    }

    fun setDisplayName(name: String): PacEntity {
        send(
            WrapperPlayServerEntityMetadata(
                id,
                listOf(
                    EntityData(3, EntityDataTypes.BOOLEAN, true),
                    EntityData(2, EntityDataTypes.OPTIONAL_COMPONENT, Optional.of(Component.text(name)))
                )
            )
        )
        return this
    }

    fun kill(): PacEntity {
        val pac = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)
        if (PacketManager.version.isAtLeast(MinecraftVersion.COMBAT_UPDATE))
            pac.intLists.write(0, listOf(id))
        else
            pac.integerArrays.write(0, intArrayOf(id))
        send(pac)
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
        send(pac)
        return this
    }


}