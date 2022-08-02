package me.alexirving.core.newitem

import me.alexirving.core.EngineManager
import me.alexirving.core.newitem.placeholder.PlaceHolder
import java.io.File
import java.io.FileReader
import java.util.*

/**
 * Represents a template item
 * @param id The id of the base item.
 * @param name The name template for the item. (Includes placeholders)
 * @param lore The lore template for the item. (Includes placeholders)
 * @param material The material template for the item. (Includes placeholders)
 * @param groupLimits The group limits.
 * @param properties The item's properties for example weather they keep it after death.
 * @param sections The sections indexed by the id with a list of their attributes.
 */
data class BaseItem(
    val id: String,
    val name: String,
    val lore: List<String>,
    val material: String,
    val groupLimits: Map<String, Int>,
    val properties: Map<ActionEvent, Boolean>,
    val placeholders: Map<String, PlaceHolder>,
    val sections: Map<String, Map<String, Attribute>>
) {
    fun getMaxAttributeLevel(section: String, attribute: String) = sections[section]?.get(attribute)?.max ?: 0

    fun getGroups(section: String, attribute: String) =
        sections[section]?.get(attribute)?.groups ?: listOf()

    /**
     * Build a default engine item using the base item
     */
    fun asEngineItem(
        manager: ItemManager,
        states: MutableMap<String, MutableMap<String, Int>> = mutableMapOf(),
        uuid: UUID = UUID.randomUUID(),
        owner: UUID? = null
    ) = EngineItem(manager, this, ItemData(id, uuid, states, owner))

    companion object {
        fun fromFile(file: File): BaseItem = EngineManager.gson.fromJson(FileReader(file), BaseItem::class.java)
    }
}