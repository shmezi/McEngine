package me.alexirving.core.sql.template

import com.fasterxml.jackson.databind.module.SimpleModule
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import me.alexirving.core.sql.MongoDb
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class BackPack(val id: String, private var contents: MutableList<ItemStack>) {

    fun setContents(contents: MutableList<ItemStack>) {
        this.contents = contents

    }

    fun open(player: Player) {
        val g = Gui.paginated().rows(6).title(Component.text("Backpack")).create()
        for (c in contents)
            g.addItem(GuiItem(c))
        g.setCloseGuiAction {
            setContents(g.inventory.contents.toMutableList())
        }
    }
}