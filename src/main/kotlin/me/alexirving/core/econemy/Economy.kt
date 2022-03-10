package me.alexirving.core.econemy

import me.alexirving.core.sql.SqLite.psExec
import me.alexirving.core.utils.nBZ
import java.util.*

class Economy(val id: String) {
    private val ecoMap = mutableMapOf<UUID, Int>()
    fun setBal(uuid: UUID, eco: Int): Int {
        ecoMap[uuid] = eco
        psExec("INSERT INTO `$id` values (`$uuid`, `$eco`) ON DUPLICATE KEY UPDATE `$uuid` = `$eco`;")
        return ecoMap[uuid] ?: 0
    }

    fun getBal(player: UUID): Int = ecoMap[player] ?: 0

    fun addBal(player: UUID, add: Int) = setBal(player, getBal(player) + add)

    fun subBal(player: UUID, remove: Int) = setBal(player, (getBal(player) - remove).nBZ())


}