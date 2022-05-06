/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * Offset.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.animation.objects

import me.alexirving.core.animation.utils.Direction
import org.bukkit.Location
import kotlin.math.pow

/**
 * Represents an offset from a [Location]
 */
class Offset : Cloneable {
    var x: Double = 0.0
    var y: Double = 0.0
    var z: Double = 0.0

    /**
     * Constructs an [Offset] from a [Location] using Doubles
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    constructor(x: Double?, y: Double?, z: Double?) {
        this.x = x ?: 0.0
        this.y = y ?: 0.0
        this.z = z ?: 0.0
    }

    /**
     * Constructs an [Offset] from a [Location] using Ints over Doubles
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    constructor(x: Int?, y: Int?, z: Int?) {
        this.x = x?.toDouble() ?: 0.0
        this.y = y?.toDouble() ?: 0.0
        this.z = z?.toDouble() ?: 0.0
    }

    /** Constructs an [Offset] from a [Location] using Strings over Doubles
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    constructor(x: String?, y: String?, z: String?) {
        this.x = x?.toDouble() ?: 0.0
        this.y = y?.toDouble() ?: 0.0
        this.z = z?.toDouble() ?: 0.0
    }

    /**
     * Gets the offset [Location] based on the given [location]
     * @param location the [Location] to base the offset off of
     * @return the offset [Location] based on the given [location]
     */
    fun getOffset(location: Location, facing: Direction): Location {
        return when (facing) {
            Direction.NORTH -> location.clone().add(x, y, z)
            Direction.SOUTH -> location.clone().add((-1 * x), y, (-1 * z))
            Direction.EAST -> location.clone().add(z, y, x)
            Direction.WEST -> location.clone().add((-1 * z), y, (-1 * x))
        }

    }

    fun length() = (this.x.pow(2) + this.y.pow(2) + this.z.pow(2)).pow(0.5); //sqrt(x^2 + y^2 + z^2)

    fun add(offset: Offset) {
        this.x += offset.x
        this.y += offset.y
        this.z += offset.z
    }

    fun cross(offset: Offset) {
        val x = x
        val y = y
        val z = z
        this.x = y * offset.z - z * offset.y
        this.y = z * offset.x - x * offset.z
        this.z = x * offset.y - y * offset.x
    }

    fun normalize() {
        val length = length()
        if (length == 0.0) return
        x /= length
        y /= length
        z /= length
    }

    fun scale(scaleFactor: Double) {
        x *= scaleFactor
        y *= scaleFactor
        z *= scaleFactor
    }

    /**
     * Gets a pretty print representation of the offset
     * @return a pretty print representation of the offset
     */
    override fun toString(): String {
        return "[$x, $y, $z]"
    }

    public override fun clone(): Offset {
        return super.clone() as Offset
    }

    companion object {
        fun add(v1: Offset, v2: Offset) = v1.clone().apply { add(v2) }
        fun cross(v1: Offset, v2: Offset) = v1.clone().apply { cross(v2) }
        fun dot(v1: Offset, v2: Offset) = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
        fun scale(v: Offset, scalar: Double) = v.clone().apply { scale(scalar) }
        fun normalize(v: Offset) = v.clone().apply { normalize() }
    }
}