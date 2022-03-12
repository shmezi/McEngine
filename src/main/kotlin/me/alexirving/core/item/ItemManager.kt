package me.alexirving.core.item

import com.google.gson.Gson
import de.tr7zw.changeme.nbtapi.NBTItem
import me.alexirving.core.exceptions.CompileError
import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.filters.BaseItemFilter
import org.bukkit.inventory.ItemStack
import java.io.File

object ItemManager {
    val gson = Gson()
    val bases = mutableMapOf<String, BaseItem>()
        get() = field

    fun reload(dir: File) {
        bases.clear()
        for (f in dir.listFiles(BaseItemFilter()) ?: return)
            bases[f.nameWithoutExtension] = BaseItem.fromJson(f)
    }


    fun buildFromNbt(stack: ItemStack): InstanceItem {
        val nbt = NBTItem(stack)
        val id = nbt.getString("ItemId")
        if (!bases.containsKey(id))
            throw NotFoundException("ItemId from item not found! ItemId: \"$id\"")
        return bases[id]?.asInstance(stack)?.apply {
            val m = nbt.getObject("attributes", Map::class.java) as Map<String, Int>
            for (v in m)
                setLevel(v.key, v.value)

        } ?: throw CompileError("Some odd error Shmezi#4200 should look into occurred while making an instance item!")
    }
}