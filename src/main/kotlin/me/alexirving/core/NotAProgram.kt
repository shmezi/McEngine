package me.alexirving.core

import java.awt.Frame
import java.awt.Label
import java.awt.Toolkit
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class NotAProgram : Frame("This is not an application!") {
    private val s = Label("To install please drop in a bukkit/spigot server in the /plugins/ folder!")

    init {
        s.setBounds(width / 2, height / 2, 20, 20)

        s.alignment = Label.CENTER
        add(s)
        isVisible = true
        setSize(500, 150)
        isResizable = false
        iconImage =
            Toolkit.getDefaultToolkit().createImage(Thread.currentThread().contextClassLoader.getResource("mc.png"))
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                dispose()
            }
        })
    }
}

fun main() {
    NotAProgram()
}