/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * ItemManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item

import com.google.gson.Gson
import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.exceptions.CompileError
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.item.template.BaseItem
import org.bukkit.inventory.ItemStack
import java.io.File

object ItemManager {
    val gson = Gson()
    val bases = mutableMapOf<String, BaseItem>()

    fun reload(dir: File) {
        bases.clear()
        for (f in dir.listFiles() ?: return) {
            bases[f.nameWithoutExtension] = BaseItem.fromJson(f)
            println("Loaded item: ${f.nameWithoutExtension} successfully")
        }
    }


    fun buildFromNbt(stack: ItemStack): InstanceItem {
        val nbt = NBTItem(stack)
        val id = nbt.getString("ItemId")
        if (!bases.containsKey(id))
            throw NotFoundException("ItemId from item not found! ItemId: \"$id\"")
        return bases[id]?.asInstance(stack)?.apply {
            val m = nbt.getObject("attributes", Map::class.java) as Map<String, Int>
//            for (v in m)
//                (v.key, v.value)

        } ?: throw CompileError("Some odd error Shmezi#4200 should look into occurred while making an instance item!")
    }
}