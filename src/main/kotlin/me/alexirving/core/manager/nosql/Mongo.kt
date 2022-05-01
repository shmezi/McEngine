//package me.alexirving.core.manager.nosql
//
//import com.mongodb.ConnectionString
//import com.mongodb.MongoClientSettings
//import kotlinx.coroutines.runBlocking
//
//import org.bson.UuidRepresentation
//import org.litote.kmongo.coroutine.CoroutineClient
//import org.litote.kmongo.coroutine.CoroutineDatabase
//import org.litote.kmongo.coroutine.coroutine
//import org.litote.kmongo.reactivestreams.KMongo
//import java.util.*
//
//class Mongo(connection: String) : Database {
//    lateinit var client: CoroutineClient
//    lateinit var edb: CoroutineDatabase
//
//    init {
//        dbReload(connection)
//    }
//
//    override fun dbReload(connection: String) {
//        client = KMongo.createClient(
//            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
//                .applyConnectionString(ConnectionString(connection)).build()
//        ).coroutine
//        edb = client.getDatabase("McEngine")
//    }
//
//
//    override fun <Db, T> dbGet(db: Db, key: UUID, async: (value: T) -> Unit) {
//        runBlocking {
//
//        }
//    }
//
//
//    override fun <T> dbUpdate(db: Any, key: UUID, value: T) {
//        TODO("Not yet implemented")
//    }
//}