package me.alexirving.core.database.structs


import me.alexirving.core.database.Cacheable
import me.alexirving.core.points.Points
import me.alexirving.core.utils.SimpleLocation
import java.util.*

data class GangData(
    var name: String,
    var motd: String,
    var tag: String,
    var public: Boolean,
    var owner: UUID?,
    val players: MutableList<UUID>,
    var cell: SimpleLocation?,
    val channels: MutableList<String>
) : Cacheable() {

    fun balance(e: Points, async: (amount: Double) -> Unit) = e.getPoints(getUUID(), async)

    companion object {
        fun default(owner: UUID?, name: String): GangData {
            return GangData(
                name, "", "", false, owner,
                mutableListOf<UUID>().apply { if (owner != null) add(owner) }, null, mutableListOf()
            )
        }
    }

}