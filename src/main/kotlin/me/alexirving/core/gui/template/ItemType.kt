package me.alexirving.core.gui.template

enum class ItemType {
    STATIC,
    SWITCH,
    ARRAY;

    companion object {
        fun valueOfOrNull(value: String): ItemType? {
            return try {
                valueOf(value)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }
}