package me.alexirving.core.utils

import java.io.File
import java.io.FileFilter

class FileExtensionFilter(private vararg val exts: String) : FileFilter {
    override fun accept(name: File): Boolean {
        for (ex in exts)
            if (name.endsWith(".$ex")) return true
        return false
    }

}
