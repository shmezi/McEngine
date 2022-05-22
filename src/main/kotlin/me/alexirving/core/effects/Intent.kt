package me.alexirving.core.effects

/**
 * An [Intent] defines what events a [Effect] will listen to.
 * Bitwise has not been implemented yet.
 */
enum class Intent(val id: Int) {
    /**
     * Listen to when the effect starts
     */
    START(0x10),

    /**
     * Listen to when the effect ends
     */
    RESET(0x20),

    /**
     * Listen to when player interacts
     */
    INTERACT(0x40),

    /**
     * Listen to when the player mines
     */
    MINE(0x80),

    /**
     * Listen to when the effect added to an item occurs.
     */
    BUILD(0x16),
    PLACE(0x32);
}
