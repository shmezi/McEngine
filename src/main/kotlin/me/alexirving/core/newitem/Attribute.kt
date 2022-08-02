package me.alexirving.core.newitem

/**
 * An attribute of a [BaseItem], for example an enchant of speed.
 * @param id Attribute's ID.
 * @param max The maximum level this attribute can be at (-1 for unlimited).
 * @param placeholders The placeholders at each level of the attribute. (If the max setting is unlimited, It will use the placeholders provided and once surpassed it will use the last placeholder given).
 * @param actions The actions at each level.
 * @param groups The group points at each level the attribute gives.
 */
data class Attribute(
    val id: String,
    val max: Int,
    val placeholders: List<Map<String, Int>>,
    val actions: List<List<Action>>,
    val groups: List<Map<String, Int>>
)