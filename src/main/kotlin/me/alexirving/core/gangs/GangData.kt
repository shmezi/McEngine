package me.alexirving.core.gangs


import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.points.Points
import me.alexirving.core.utils.SimpleLocation
import java.util.*

data class GangData(
    val uuid: String,
    var name: String,
    var motd: String,
    var public: Boolean,
    var owner: UUID,
    val players: MutableList<UUID>,
    var cell: SimpleLocation,
    val channels: MutableList<String>
) {


    @JsonIgnore
    fun getId() = UUID.fromString(uuid)

    fun balance(e: Points, async: (amount: Double) -> Unit) = e.getPoints(getId(), async)


}