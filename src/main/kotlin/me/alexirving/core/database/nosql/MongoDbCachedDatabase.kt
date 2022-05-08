package me.alexirving.core.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.ReplaceOptions
import kotlinx.coroutines.runBlocking
import me.alexirving.core.database.Cacheable
import me.alexirving.core.database.Database
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
import org.litote.kmongo.ensureUniqueIndex
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import java.util.*

class MongoDbCachedDatabase<T : Cacheable>(val id: String, val type: Class<T>, connection: String) :
    Database<Cacheable> {
    lateinit var client: MongoClient
    private lateinit var edb: MongoDatabase
    private lateinit var ec: MongoCollection<T>

    init {
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
        dbReload(connection)
    }

    override fun dbReload(connection: String) {
        client = KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        )
        edb = client.getDatabase("McEngine")
        ec = edb.getCollection(id, type)
        runBlocking {
            ec.ensureUniqueIndex(Cacheable::uuid)

        }
    }

    override fun dbDelete(key: UUID) {
        runBlocking {
            ec.deleteOne(Cacheable::uuid eq key.toString())

        }
    }

    override fun dbUpdate(value: Cacheable) {
        runBlocking {
            ec.replaceOne(Cacheable::uuid eq value.uuid, value as T, ReplaceOptions().upsert(true))
        }

    }

    override fun dbGet(key: UUID, async: (value: Cacheable?) -> Unit) {
        runBlocking {
            async(ec.findOne(Cacheable::uuid eq key.toString()))

        }
    }


}