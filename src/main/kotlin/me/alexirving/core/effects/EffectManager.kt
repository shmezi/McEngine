package me.alexirving.core.effects

import me.alexirving.core.EngineManager
import me.alexirving.core.exceptions.AlreadyExists
import me.alexirving.core.item.EngineItemListener
import me.alexirving.core.item.instance.EngineItem
import me.alexirving.core.utils.Colors
import me.alexirving.core.utils.color
import me.alexirving.core.utils.registerListeners
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

class EffectManager(private val m: EngineManager) : Listener {
    private val registeredEffects = mutableMapOf<String, Effect>()
    private val listens = mutableMapOf<Intent, MutableList<Effect>>()

    init {
        registerListeners(m.engine, EngineItemListener(m))
    }

    fun getEffectById(id: String) = registeredEffects[id]
    fun getEffectIds() = registeredEffects.keys

    @EventHandler
    fun onPlayerMine(e: BlockBreakEvent) {
        if (e.block.type == Material.WATER) return
        m.user.get(e.player.uniqueId) { userData ->
            val ae = userData.activeEffects
            listens[Intent.MINE]?.filter { ae.containsKey(it) }?.forEach {
                it.onMine(e, ae[it] ?: 0)

            }
        }
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        m.user.get(e.player.uniqueId) { userData ->
            val ae = userData.activeEffects
            listens[Intent.INTERACT]?.filter { ae.containsKey(it) }?.forEach {
                it.onInteract(e, ae[it] ?: 0)
            }
        }
    }

    @EventHandler
    fun onPlayerPlace(e: BlockPlaceEvent) {
        m.user.get(e.player.uniqueId) { userData ->
            val ae = userData.activeEffects
            listens[Intent.PLACE]?.filter { ae.containsKey(it) }?.forEach {
                it.onPlace(e, ae[it] ?: 0)
            }
        }
    }

    fun onBuild(item: EngineItem, effect: Effect, level: Int) {
        if (effect.listenTo.contains(Intent.BUILD))
            effect.onEngineItemBuild(item, level)

    }

    fun onStart(player: Player, effect: Effect, level: Int) {
        if (effect.listenTo.contains(Intent.START))
            effect.onStart(player, level)
    }

    fun onReset(player: Player, effect: Effect) {
        if (effect.listenTo.contains(Intent.RESET))
            effect.onEnd(player)
    }


    fun register(vararg effects: Effect) = effects.forEach { effect ->
        if (registeredEffects.containsKey(effect.id)) throw AlreadyExists("Effect of id ${effect.id} already exists!")
        registeredEffects[effect.id] = effect
        effect.listenTo.forEach {
            listens.getOrPut(it) { mutableListOf() }.add(effect)
        }
        println("Registered effect: \"${effect.id}\".".color(Colors.BLUE))
    }

    fun unRegister(id: String) {
        val e = registeredEffects[id] ?: return
        e.listenTo.forEach {
            listens[it]?.remove(e)
        }
    }
}
