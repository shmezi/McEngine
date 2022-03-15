/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Column.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.sql

data class Column(val name: String, val dataType: Any, val default: Any?)
