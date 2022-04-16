package me.alexirving.core.db

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.toId
import java.util.*

/**
 * Represents a user's data in the database.
 * @param uuid The player's ID.
 * @param ecos The player's ecos (for each currency).
 * @param channel The player's selected channel to chat in, if any.
 */
data class UserData(val uuid: UUID, val ecos: MutableMap<String, Double>, val channel: String?) {
    @BsonId
    val id = uuid.toString().toId<UserData>()
}