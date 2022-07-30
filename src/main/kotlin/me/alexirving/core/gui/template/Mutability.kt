package me.alexirving.core.gui.template

/**
 * Defines when items should be stored
 */
enum class Mutability {
    /**
     * Keep item states and variables even after reboot.
     */
    ALWAYS,

    /**
     * Keep item states and variables until reboot.
     */
    REBOOT,

    /**
     * Keep item states and variables until gui is closed.
     */
    NEVER;
}