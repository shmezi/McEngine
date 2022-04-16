package me.alexirving.core.effects.effects

import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.item.instance.EngineItem
import org.bukkit.enchantments.Enchantment

class Fortune : Effect("fortune", Intent.BUILD) {
    override fun onBuild(item: EngineItem, level: Int) {
        item.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, level)
    }
}