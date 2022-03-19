/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * BaseItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.template

import com.google.gson.Gson
import me.alexirving.core.item.InstanceItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

/**
 * [BaseItem] represents an item template that can be easily modified.
 * @param groups Allowed amounts per groups useful for stuff like limiting type of enchant. Groups
 * @param
 */
class BaseItem(
    val id: String,
    val material: Material,
    val displayName: String,
    val lore: List<String>,
    val placeholders: Map<String, List<String>>,
    val groups: Map<String, Int>,
    val sections: Map<String, List<Attribute>>,
) {
    companion object {
        private val g = Gson()
        fun fromJson(f: File): BaseItem = g.fromJson(BufferedReader(FileReader(f)), BaseItem::class.java)
    }

    fun asInstance(item: ItemStack, uuid: UUID) = InstanceItem(this, uuid)
    fun asInstance() = InstanceItem(this, UUID.randomUUID())
}