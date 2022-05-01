package me.alexirving.core.effects.effects

import me.alexirving.core.EngineManager
import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.utils.nBZ
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.Random
import kotlin.random.nextInt

class Jackhammer(val m: EngineManager) : Effect("jackhammer", Intent.MINE) {
    override fun onMine(e: BlockBreakEvent, level: Int) {
        val player = e.player
        m.mine.isInPlayerMine(player, e.block.location) { inMine ->
            if (!inMine) return@isInPlayerMine
            if ((0..(level - 1).nBZ()).contains(Random.nextInt(0..100))) {
                m.mine.getPlayerMine(player) {
                    it?.breakMine(player)
                }
            }
        }

    }
}