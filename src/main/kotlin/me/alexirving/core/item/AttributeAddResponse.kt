package me.alexirving.core.item

/**
 * Represents a response when attempting to level up/down an attribute of an itemInstance
 */
enum class AttributeAddResponse {
    /**
     * Added attribute successfully
     */
    SUCCESSFUL,

    /**
     * Over max group count
     */
    GROUP,

    /**
     * Over max level for attribute
     */
    MAX,

    /**
     * Attribute already removed
     */
    MIN
}