package me.alexirving.core.effects

/**
 * An [Intent] defines what events a [Effect] will listen to.
 */
enum class Intent {
    /**
     * Listen to when the effect starts
     */
    START,
    /**
     * Listen to when the effect ends
     */
    RESET,
    /**
     * Listen to when player interacts
     */
    INTERACT,
    /**
     * Listen to when the player mines
     */
    MINE,
    /**
     * Listen to when the effect added to an item occurs.
     */
    BUILD
}