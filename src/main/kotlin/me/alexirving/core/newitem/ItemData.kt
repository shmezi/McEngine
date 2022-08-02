package me.alexirving.core.newitem

import me.alexirving.lib.database.Cacheable
import java.util.*

/**
 * Defines an item's data stored in the db.
 * @param baseId The baseId of the item
 * @param uuid The uuid of the item (an identifier is also stored on the itemstack)
 * @param levels Current attribute levels per section
 * @param ownerUUID The owner uuid for the item (if needed, this is required for stuff like player's balance and username)
 * @param ownerName The owner name for the item (if needed)
 * */
data class ItemData(
    val baseId: String,
    val uuid: UUID = UUID.randomUUID(),
    val levels: MutableMap<String, MutableMap<String, Int>> = mutableMapOf(),
    var ownerUUID: UUID? = null,
    var ownerName: String? = null
) : Cacheable<UUID>(uuid)