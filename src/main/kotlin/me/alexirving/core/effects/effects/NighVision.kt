package me.alexirving.core.effects.effects

import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.utils.nBZ
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class NighVision : Effect("night_vision", Intent.START, Intent.RESET) {
    override fun onStart(player: Player, level: Int) {
        player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 999999, (level - 1).nBZ(), true, false))

    }


    override fun onReset(player: Player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION)
    }
}