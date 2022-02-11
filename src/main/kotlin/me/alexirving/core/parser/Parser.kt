package me.alexirving.core.parser

import java.io.File
import java.util.*

class Parser {
    val configV = "".toPattern()
    fun parse(file: File): String {
        val config = mutableMapOf<String, Any>()
        val sc = Scanner(file)
        sc.delimiter()
        var currentState: State? = null
//        var statementStack =
        while (sc.hasNextLine()) {
            val line = sc.nextLine()

        }
        return "Hello, World!"
    }
}

fun main() {
    val f = File("Example.animation")
    val v = Parser()
    v.parse(f)
}