/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Attribute.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.item.template

open class Attribute(
    val id: String,
    val groups: List<String>,
    val max: Int,
    val placeholders: Map<String, String>,
)