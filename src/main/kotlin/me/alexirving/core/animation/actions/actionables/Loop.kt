package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.McEngine
import me.alexirving.core.animation.AniCompiler
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.exceptions.CompileError
import java.util.regex.Pattern

class Loop(pl: McEngine, raw: String, start: Int) : SuperAction(pl, raw, start) {
    val m = Pattern.compile(".+\\((.+\\(.*\\));(\\d+)\\)")
    override fun build(): MutableMap<Int, Action> {
        val f = m.matcher(raw)
        val a = mutableMapOf<Int, Action>()
        if (f.matches()) {
            val end = f.group(2).toInt() + start
            for (i in start until end) {
                a[i] = AniCompiler.compileAction(pl, f.group(1))
            }

        } else throw CompileError("Loop has Invalid syntax")
        return a
    }
}