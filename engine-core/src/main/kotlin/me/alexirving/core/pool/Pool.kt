/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Pool.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.pool

import me.alexirving.core.exceptions.NotFoundException

class Pool<T : Poolable> {
    var max: Int
    private val type: Class<T>
    private val available = mutableListOf<T>()
    private val inuse = mutableListOf<T>()

    constructor(type: Class<T>, max: Int) {
        this.max = max
        this.type = type

    }

    constructor(type: Class<T>) {
        this.max = -1
        this.type = type

    }

    fun get(): T {
        return if (available.isNotEmpty()) {
            val a = available[0]
            inuse.add(a)
            available.remove(a)
            a
        } else
            if (max == -1 || max > inuse.size) {
                val a = type.getDeclaredConstructor().newInstance()
                inuse.add(a)
                a
            } else
                throw NotFoundException("No more objects are available in pool of `$type`!")
    }

    fun sleep(v: T) {
        inuse.remove(v)
        v.reset()
        available.add(v)
    }

}