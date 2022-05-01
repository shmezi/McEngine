package me.alexirving.core.gangs


import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.points.Points
import me.alexirving.core.utils.SimpleLocation
import java.util.*

class GangData(
    val name: String,
    val owner: UUID,
    val players: MutableList<UUID>,
    val cell: SimpleLocation,
    val channels: MutableList<String>
) {
    val uuid = UUID.randomUUID().toString()

    @JsonIgnore
    fun getId() = UUID.fromString(uuid)
    fun balance(e: Points, async: (amount: Double) -> Unit) = e.getPoints(getId(), async)


}