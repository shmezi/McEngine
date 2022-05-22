package me.alexirving.core.effects.effects

import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.utils.nBZ
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Speed : Effect("speed", Intent.START, Intent.RESET) {
    override fun onStart(player: Player, level: Int) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 999999, (level - 1).nBZ(), false, false))

    }


    override fun onEnd(player: Player) {
        player.removePotionEffect(PotionEffectType.SPEED)
    }
}