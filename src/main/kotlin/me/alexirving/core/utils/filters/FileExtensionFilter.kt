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
