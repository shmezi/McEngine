/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Attribute.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.template

/**
 * Represents an [Attribute] of a [BaseItem]
 * @param id ID of the attribute
 * @param groups The group IDs that this [Attribute] uses
 * @param max The max level of this [Attribute]
 * @param placeholders Placeholder replacements (key = placeholder) | (value = list of placeholder levels)
 * @param effects The effects the [Attribute] can give the player
 */
class Attribute(
    val id: String,
    val groups: List<String>,
    val max: Int,
    val placeholders: Map<String, List<Int>>,
    val effects: List<Map<String, Int>>
)