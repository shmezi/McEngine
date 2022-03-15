/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * BaseItemFilter.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.utils.filters

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.File
import java.io.FileFilter
import java.io.FileReader

class BaseItemFilter : FileFilter {
    private val gson = Gson()
    override fun accept(f: File): Boolean {
        return if (f.isFile && f.extension == "json")
            try {
                gson.fromJson(BufferedReader(FileReader(f)), f::class.java)
                true
            } catch (ignored: Exception) {
                false
            }
        else
            false
    }
}