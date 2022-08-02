package me.alexirving.core.newitem

import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.lib.database.nosql.MongoConnection
import me.alexirving.lib.database.nosql.MongoDbCachedCollection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.io.File
import java.util.*

class ItemManager(connection: MongoConnection) : Listener {
    private val itemsCache = mutableMapOf<UUID, EngineItem>()

    private val itemDB =
        MongoDbCachedCollection("Items", ItemData::class.java, connection).getManager(ItemData("empty"))

    /**
     * Updates an item
     */
    private val bases = mutableMapOf<String, BaseItem>()


    fun createItem(baseId: String): EngineItem? {
        val item = bases[baseId]?.asEngineItem(this) ?: return null
        val uuid = item.data.uuid
        itemsCache[uuid] = item
        itemDB.get(uuid){}
        return null
    }

    fun reload(dataFolder: File) {
        bases.clear()
        for (f in dataFolder.listFiles() ?: return) {
            if (f.extension != "json")
                continue
            val b = BaseItem.fromFile(f)
            bases[b.id] = b
            println("Loaded item: ${f.nameWithoutExtension} (Id: ${b.id}) successfully".color(Colors.CYAN))
        }
    }


    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        e.hand ?: return
    }
}