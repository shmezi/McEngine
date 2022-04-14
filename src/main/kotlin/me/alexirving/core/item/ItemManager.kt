/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * ItemManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item

//import me.alexirving.core.sql.MongoDb.getUser
import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File

class ItemManager(val dataFolder: File) {
    val bases = mutableMapOf<String, BaseItem>()
    private val materialSet = mutableSetOf<Material>()
    private val idSet = mutableSetOf<String>()

    init {
        reload()
    }

    fun isCustom(item: ItemStack?): Boolean {
        item ?: return false
        if (!materialSet.contains(item.type)) return false
        val nbt = NBTItem(item)
        if (!nbt.hasNBTData()) return false
        if (!idSet.contains(nbt.getString("id"))) return false
        return true
    }


    fun reload() {
        bases.clear()
        materialSet.clear()
        idSet.clear()
        for (f in dataFolder.listFiles() ?: return) {
            if (f.extension != "json")
                continue
            val b = BaseItem.fromJson(f)
            materialSet.add(b.material)
            idSet.add(b.id)
            bases[b.id] = b
            b.init()
            println("Loaded item: ${f.nameWithoutExtension} (Id: ${b.id}) successfully".color(Colors.CYAN))
        }
    }


    fun ItemStack.isEngineItem() = isCustom(this)
    fun ItemStack.asEngineItemOrNull() {

    }

}
