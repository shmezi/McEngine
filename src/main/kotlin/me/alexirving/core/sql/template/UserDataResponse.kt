package me.alexirving.core.sql.template

import me.alexirving.core.sql.MongoDb

data class UserDataResponse(
    val uuid: String,
    val pickLevel: Int,
    val mined: Long,
    val ecos: MutableMap<String, Double>
) {
    fun setBal(id: String, value: Double) {
        ecos[id] = value
//        MongoDb.setValue(uuid, UserDataResponse::ecos, ecos)
    }

//    fun setMined(value: Double) = MongoDb.setValue(uuid, UserDataResponse::mined, value)
//    fun setPickLevel(value: Int) = MongoDb.setValue(uuid, UserDataResponse::pickLevel, value)

}