package me.alexirving.core.item.template

import com.google.gson.Gson
import me.alexirving.core.item.InstanceItem
import org.bukkit.Material
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

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
    val sections: List<Section>,
) {
    companion object {
        private val g = Gson()
        fun fromJson(f: File): BaseItem = g.fromJson(BufferedReader(FileReader(f)), BaseItem::class.java)
    }

    fun buildInstance() = InstanceItem(this)
}

fun main() {
    val a = BaseItem.fromJson(File("SuperPick.json"))
    println(a.toString())
}