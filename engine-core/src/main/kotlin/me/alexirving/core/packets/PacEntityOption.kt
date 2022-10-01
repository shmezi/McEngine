package me.alexirving.core.packets

enum class PacEntityOption(val bitMask: Int) {
    FIRE(0x01),
    CROUCH(0x02),
    RIDING(0x03)/*Unused*/,
    SPRINT(0x04),
    SWIM(0x10),
    INVISIBLE(0x20),
    GLOW(0x40),
    ELYTA(0x80)
}