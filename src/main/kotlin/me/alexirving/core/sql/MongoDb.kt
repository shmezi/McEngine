package me.alexirving.core.sql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import me.alexirving.core.sql.template.BackPack
import me.alexirving.core.sql.template.UserDataResponse
import org.bson.UuidRepresentation
import org.litote.kmongo.*
import kotlin.reflect.KProperty

object MongoDb {
    private var db = KMongo.createClient()

    fun init(connection: String) {
        db = KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        )
    }

    fun getDb(): MongoDatabase = db.getDatabase("McEngine")
    private fun getUserDb() = getDb().getCollection<UserDataResponse>("userData")
    private fun getBPDb() = getDb().getCollection<BackPack>("backpackData")


    fun createUser(uuid: String): UserDataResponse {
        val u = UserDataResponse(uuid, 0, 0L, mutableMapOf())
        getUserDb().insertOne(u)
        return u
    }


    fun createBackPack(uuid: String): BackPack {
        val u = BackPack(uuid, mutableListOf())
        getBPDb().insertOne(u)
        return u
    }

    fun getBackPack(uuid: String): BackPack = getBPDb().findOne(BackPack::id eq uuid) ?: createBackPack(uuid)


    fun getUser(uuid: String): UserDataResponse =
        getUserDb().findOne(UserDataResponse::uuid eq uuid) ?: createUser(uuid)

    fun setValue(uuid: String, key: KProperty<Any>, value: Any): UserDataResponse? =
        getUserDb().findOneAndUpdate(UserDataResponse::uuid eq uuid, setValue(key, value))
}