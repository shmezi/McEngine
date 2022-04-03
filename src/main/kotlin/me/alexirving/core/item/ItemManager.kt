/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * ItemManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item

//import me.alexirving.core.sql.MongoDb.getUser
import com.google.gson.Gson
import me.alexirving.core.item.template.BaseItem
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import java.io.File

object ItemManager {
    val gson = Gson()
    val bases = mutableMapOf<String, BaseItem>()

    fun reload(dir: File) {
        bases.clear()
        for (f in dir.listFiles() ?: return) {
            val b = BaseItem.fromJson(f)
            bases[b.id] = b
            b.init()
            println("Loaded item: ${f.nameWithoutExtension} (Id: ${b.id}) successfully".color(Colors.PURPLE))
        }
    }

}
