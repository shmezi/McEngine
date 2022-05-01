package me.alexirving.core

import java.awt.Frame
import java.awt.Label
import java.awt.event.KeyListener
import java.awt.event.WindowAdapter

class NotAProgram : Frame("This is not an application!") {
    private val s = Label("To install please drop in a bukkit/spigot server in the /plugins/ folder!")

    init {
        s.setBounds(20, 50, 80, 20)
        add(s)
        isVisible = true
        setSize(1000, 1000)
    }


}

fun main() {
    val s = NotAProgram()
}