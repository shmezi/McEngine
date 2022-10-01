/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Empty.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.actions.Action
import me.alexirving.core.gui.GuiManager
import org.bukkit.entity.Player

class GuiClose(args: Map<String, Any>) : Action(args) {
    override val id = "blank"
    override fun run(data: MutableMap<String, Any>) {
        GuiManager.close((data["viewers"] as List<Player>?) ?: return)
    }
}