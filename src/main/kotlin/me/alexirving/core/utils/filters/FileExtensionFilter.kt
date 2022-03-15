/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * FileExtensionFilter.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.utils.filters

import java.io.File
import java.io.FileFilter

class FileExtensionFilter(private vararg val allowed: String) : FileFilter {
    override fun accept(f: File): Boolean {
        for (x in allowed)
            if (f.extension == x) return true
        return false
    }

}
