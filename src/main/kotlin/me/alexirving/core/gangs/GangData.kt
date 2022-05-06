package me.alexirving.core.gangs


import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.EngineManager
import me.alexirving.core.points.Points
import me.alexirving.core.utils.SimpleLocation
import java.util.*

data class GangData(
    val uuid: String,
    var name: String,
    var motd: String,
    var tag: String,
    var public: Boolean,
    var owner: UUID,
    val players: MutableList<UUID>,
    var cell: SimpleLocation?,
    val channels: MutableList<String>
) {


    @JsonIgnore
    fun getId(): UUID = UUID.fromString(uuid)

    fun balance(e: Points, async: (amount: Double) -> Unit) = e.getPoints(getId(), async)

    companion object {
        fun default(m: EngineManager, owner: UUID, name: String): GangData {
            val uuid = UUID.randomUUID()
            return GangData(
                uuid.toString(), name, "", "", false, owner,
                mutableListOf(owner), null, mutableListOf()
            )
        }
    }

}