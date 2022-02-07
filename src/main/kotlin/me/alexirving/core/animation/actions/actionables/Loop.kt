package me.alexirving.core.animation.actions.actionables
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import me.alexirving.core.animation.AniCompiler
import me.alexirving.core.animation.actions.Action
import me.alexirving.core.animation.actions.SuperAction
import me.alexirving.core.animation.exceptions.CompileError
import me.alexirving.core.animation.packets.PacketManager
import me.alexirving.core.items.ItemManager
import java.util.regex.Pattern

class Loop(pm: PacketManager, im: ItemManager, raw: String, start: Int) : SuperAction(pm, im, raw, start) {
    val m = Pattern.compile(".+\\((.+\\(.*\\));(\\d+)\\)")
    override fun build(): MutableMap<Int, Action> {
        val f = m.matcher(raw)
        val a = mutableMapOf<Int, Action>()
        if (f.matches()) {

            for (i in start until f.group(2).toInt()) {
                a[i] = AniCompiler.compileAction(pm, im, f.group(1))
            }

        } else throw CompileError("Loop has Invalid syntax")
        return a
    }
}