/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * BaseItem.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.template

import com.google.gson.Gson
import de.tr7zw.changeme.nbtapi.NBTItem
import dev.triumphteam.gui.components.util.Legacy
import me.alexirving.core.item.InstanceItem
import me.alexirving.core.item.InventoryReference
import me.alexirving.core.utils.pq
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * [BaseItem] represents an item template that can be easily modified.
 * @param id The Identifier for the baseItem.
 * @param material [Material] of the baseItem.
 * @param displayName Template display name.
 * @param lore Template lore of baseItem.
 * @param placeholders a map containing the placeholder with a list of options.
 * @param groups Allowed amounts per groups useful for stuff like limiting type of enchant. Groups
 * @param sections Sections allow lists of attributes allowed in each section
 *
 */
class BaseItem(
    val id: String,
    val material: Material,
    val displayName: String,
    val lore: List<String>,
    val placeholders: Map<String, List<String>>,
    val groups: Map<String, Int>,
    val sections: Map<String, List<Attribute>>
) {

    private lateinit var template: ItemStack


    /**
     * Google serialization will not init everything smh
     */
    fun init() {

        template = ItemStack(material)
        val im = template.itemMeta
        val mm = MiniMessage.miniMessage()
        val ss = Legacy.SERIALIZER
        im.displayName = ss.serialize(mm.deserialize(displayName))
        im.lore = lore.map { ss.serialize(mm.deserialize(it)) }
        template.itemMeta = im

        NBTItem(template, true).apply {
            setString("id", id)
        }
    }

    fun getTemplate() = template.clone()


    companion object {
        private val g = Gson()
        fun fromJson(f: File): BaseItem = g.fromJson(BufferedReader(FileReader(f)), BaseItem::class.java)
    }

    fun asInstance(reference: InventoryReference) = InstanceItem(this, reference)

}