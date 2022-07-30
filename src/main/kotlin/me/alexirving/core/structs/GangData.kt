package me.alexirving.core.structs


import me.alexirving.core.points.Points
import me.alexirving.core.utils.SimpleLocation
import me.alexirving.lib.database.Cacheable
import java.util.*

class GangData(
    uuid: UUID,
    var name: String,
    var motd: String,
    var tag: String,
    var public: Boolean,
    var owner: UUID?,
    val players: MutableList<UUID>,
    var cell: SimpleLocation?,
    val channels: MutableList<String>
) : Cacheable<UUID>(uuid) {


    fun balance(e: Points, async: (amount: Double) -> Unit) = e.getPoints(identifier, async)

    companion object {
        fun default(owner: UUID?, name: String): GangData {
            return GangData(
                UUID.randomUUID(),
                name,
                "",
                "",
                false,
                owner,
                mutableListOf<UUID>().apply { if (owner != null) add(owner) },
                null,
                mutableListOf()
            )
        }
    }

}