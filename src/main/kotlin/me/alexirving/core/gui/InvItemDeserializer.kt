package me.alexirving.core.gui

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import me.alexirving.core.gui.template.ItemType
import me.alexirving.core.gui.template.ItemType.*
import me.alexirving.core.gui.template.item.InvItem

class InvItemDeserializer : StdDeserializer<InvItem>(InvItem::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): InvItem {
        val node: JsonNode = p.codec.readTree(p)
        val item: InvItem
        val t = ItemType.valueOfOrNull(node.get("type").asText()) ?: throw IllegalArgumentException(
            "GUI Inventory item not found, for item with type of \"${
                node.get("type").asText()
            }\""
        )
        item = when (t) {
            STATIC -> TODO()

            SWITCH -> TODO()
            ARRAY -> TODO()
        }
    }
}