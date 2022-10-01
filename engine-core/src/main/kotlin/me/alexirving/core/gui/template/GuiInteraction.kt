package me.alexirving.core.gui.template

import dev.triumphteam.gui.guis.BaseGui
import dev.triumphteam.gui.guis.Gui
import kotlin.reflect.KFunction1

enum class GuiInteraction(val f: KFunction1<Gui, BaseGui>) {
    PLACE(Gui::disableItemPlace),
    TAKE(Gui::disableItemTake),
    SWAP(Gui::disableItemSwap),
    DROP(Gui::disableItemDrop),
    OTHER(Gui::disableOtherActions),
    ALL(Gui::disableAllInteractions)


}
