package me.alexirving.core.item.objects

import com.google.gson.Gson
import org.bukkit.Material
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

open class BaseItem : Cloneable {
    var displayName = ""
    var material: Material
    val groups = mutableListOf<Group>()
    val sections = mutableListOf<Section>()

    constructor(material: Material) {
        this.material = material
    }

    constructor(material: Material, name: String) {
        this.material = material
        this.displayName = name
    }

    companion object {
        private val gson = Gson()
        fun loadFromJson(file: File) = gson.fromJson(BufferedReader(FileReader(file)), BaseItem::class.java)

    }

    fun buildInstance() = ItemInstance(this.clone() as BaseItem)
}