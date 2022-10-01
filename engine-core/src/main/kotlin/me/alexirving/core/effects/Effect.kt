package me.alexirving.core.effects

import me.alexirving.core.item.instance.EngineItem
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent


/**
 *An [Effect] Represents a real world change non-cosmetic such as adding speed or altering a block
 * @param id ID of effect
 */
abstract class Effect(val id: String, vararg val listenTo: Intent) {

    /**
     * Run when effect starts
     */
    open fun onStart(player: Player, level: Int) {}

    /**
     * Run when effect ends
     */
    open fun onEnd(player: Player) {}

    /**
     * Run when player interacts
     *
     * (Not recommended as this can be pretty heavy.)
     */
    open fun onInteract(e: PlayerInteractEvent, level: Int) {}

    /**
     * Run when player mines
     */
    open fun onMine(e: BlockBreakEvent, level: Int) {}

    /**
     * Run when a player breaks a block
     */
    open fun onPlace(e: BlockPlaceEvent, level: Int) {}

    /**
     * Run when an engineItem is built
     */
    open fun onEngineItemBuild(item: EngineItem, level: Int) {}

}

