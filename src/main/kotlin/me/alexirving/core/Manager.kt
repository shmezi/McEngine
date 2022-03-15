/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Manager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core

import me.alexirving.core.animation.AnimationManager
import java.io.File

object Manager {
    private var hasSarted = false
    private lateinit var engine: McEngine
    private lateinit var dataFolder: File
    private lateinit var am: AnimationManager
    fun start(engine: McEngine, dataFolder: File) {
        this.engine = engine
        this.dataFolder = dataFolder
        this.am = AnimationManager(Manager.dataFolder, Manager.engine)
    }

    fun animationManager() = am
}