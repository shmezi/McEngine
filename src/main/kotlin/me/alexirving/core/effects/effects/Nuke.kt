package me.alexirving.core.effects.effects

import me.alexirving.core.EngineManager
import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.utils.nBZ
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.Random
import kotlin.random.nextInt

class Nuke(val m: EngineManager) : Effect("nuke", Intent.MINE) {
    override fun onMine(e: BlockBreakEvent, level: Int) {
        m.mine.isInPlayerMine(e.player, e.block.location) { inMine ->
            if (!inMine) return@isInPlayerMine
            if ((0..(level - 1).nBZ()).contains(Random.nextInt(0..100))) {
                m.mine.getPlayerMine(e.player) {
                    it?.breakLayer(e.block.location, e.player)
                }
            }
        }
    }
}