package me.alexirving.core.effects.effects

import me.alexirving.core.effects.Effect
import me.alexirving.core.effects.Intent
import me.alexirving.core.item.instance.EngineItem
import org.bukkit.enchantments.Enchantment

class Efficiency: Effect("efficiency", Intent.ADD) {
    override fun onAdd(item: EngineItem, level: Int) {
        item.addEnchant(Enchantment.DIG_SPEED, level)
    }

}