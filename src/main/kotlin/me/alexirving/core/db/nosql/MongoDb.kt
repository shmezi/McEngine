package me.alexirving.core.db.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.coroutines.runBlocking
import me.alexirving.core.channels.ChannelData
import me.alexirving.core.db.Database
import me.alexirving.core.db.UserData
import me.alexirving.core.exceptions.ShmeziFuckedUp
import me.alexirving.core.gangs.GangData
import me.alexirving.core.mines.PrisonSettings
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.pq
import org.bson.UuidRepresentation
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.util.*

class MongoDb : Database {
    private lateinit var client: CoroutineClient

    private lateinit var db: CoroutineDatabase
    private lateinit var userDb: CoroutineCollection<UserData>
    private lateinit var channelDb: CoroutineCollection<ChannelData>
    private lateinit var gangDb: CoroutineCollection<GangData>

    init {
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
    }

    override fun dbReload(connection: String) {

        client = KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        ).coroutine
        db = client.getDatabase("UserData")
        userDb = db.getCollection("UserData")
        channelDb = db.getCollection("ChannelData")
        gangDb = db.getCollection("GangData")
        runBlocking {
            userDb.ensureUniqueIndex(UserData::uuid)
            channelDb.ensureUniqueIndex(ChannelData::uuid)
            gangDb.ensureUniqueIndex(GangData::uuid)
        }
        println("Reloading MongoDb".color(Colors.BLUE))
    }

    override fun dbGetUser(uuid: UUID, async: (userData: UserData) -> Unit) {
        runBlocking {
            val userDataResponse = userDb.findOne(UserData::uuid eq uuid.toString())
            var user: UserData? = null
            if (userDataResponse == null) {
                user = UserData(
                    uuid.toString(), mutableMapOf(), PrisonSettings.default(), null,
                    channels = mutableSetOf()
                )
                userDb.insertOne(user)
            }
            async(userDataResponse ?: user ?: throw ShmeziFuckedUp("M8 I DONT KNOW..."))
        }
    }

    override fun dbGetChannel(uuid: UUID, success: (channel: ChannelData) -> Unit, failure: () -> Unit) {
        runBlocking {
            val channel = channelDb.findOne(ChannelData::uuid eq uuid.toString())
            if (channel != null)
                success(channel)
            else
                failure()

        }
    }

    override fun dbUpdateChannel(channel: ChannelData) {
        runBlocking {
            val c = userDb.findOneById(channel.uuid)
            if (c == null)
                channelDb.insertOne(channel)
            else
                channelDb.replaceOneById(channel.uuid, channel)
        }
    }

    override fun dbUpdateGang(gang: GangData) {
        "Creating a gang ${gang.uuid}".pq("COOL")

        runBlocking {
            val c = gangDb.findOne(GangData::uuid eq gang.uuid)
            if (c == null)
                gangDb.insertOne(gang)
            else
                gangDb.replaceOne(GangData::uuid eq gang.uuid, gang)
        }

    }

    override fun dbGetGang(uuid: UUID, success: (channel: GangData) -> Unit, failure: () -> Unit) {
        runBlocking {
            val g = gangDb.findOne(GangData::uuid eq uuid.toString())
            if (g != null)
                success(g)
            else
                failure()

        }
    }

    override fun dbDeleteGang(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun dbGetUsers(async: (userData: List<UserData>) -> Unit) {
        runBlocking {
            async(userDb.find().toList())
        }
    }


    override fun dbUpdateUser(userData: UserData) {
        runBlocking {
            userDb.replaceOneById(userData.uuid, userData)
        }
    }

    override fun dbUpdateUsers(users: List<UserData>) {
        runBlocking {
            users.forEach { userDb.replaceOne(UserData::uuid eq it.uuid, it) }
        }
    }


}