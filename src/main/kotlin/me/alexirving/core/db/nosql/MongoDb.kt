package me.alexirving.core.db.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.coroutines.runBlocking
import me.alexirving.core.db.Database
import me.alexirving.core.db.UserData
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.pq
import org.bson.UuidRepresentation
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.util.*

class MongoDb(connection: String) : Database {
    private var client = KMongo.createClient(
        MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(connection)).build()
    ).coroutine

    private val db = client.getDatabase("UserData")
    private val userDataDb = db.getCollection<UserData>("UserData")
    override fun reload(connection: String) {
        client = KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        ).coroutine

        println("Starting MongoDb".color(Colors.BLUE))
    }

    override fun getUser(uuid: UUID, async: (userData: UserData) -> Unit) {
        runBlocking {
            val ud = userDataDb.findOneById(uuid.toString())
            val u = UserData(uuid, mutableMapOf(), null)
            if (ud == null)
                userDataDb.insertOne(u)
            async(ud ?: u)
        }
    }

    override fun getUsers(async: (userData: List<UserData>) -> Unit) {
        runBlocking {
            async(userDataDb.find().toList())
        }
    }


    override fun updateUser(userData: UserData) {
        runBlocking {
            userDataDb.replaceOneById(userData.uuid.toString(), userData)
        }
    }


}