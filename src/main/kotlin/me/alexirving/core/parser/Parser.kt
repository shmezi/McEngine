/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Parser.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
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