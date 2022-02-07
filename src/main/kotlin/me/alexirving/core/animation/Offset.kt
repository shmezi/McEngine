package me.alexirving.core.animation
/* Copyright (C) AlexIrving - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, February 2022
 */
import org.bukkit.Location

/**
 * Represents an offset from a [Location]
 */
class Offset {
    private var x: Double = 0.0
    private var y: Double = 0.0
    private var z: Double = 0.0

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
    fun getOffset(location: Location): Location {
        println(location.getFacing())
        return when (location.clone().getFacing()) {
            Facing.NORTH -> location.clone().add(x, y, z)
            Facing.SOUTH -> location.clone().add((-1 * x), y, (-1 * z))
            Facing.EAST -> location.clone().add(z, y, x)
            Facing.WEST -> location.clone().add((-1 * z), y, (-1 * x))
        }

    }

    /**
     * Gets a pretty print representation of the offset
     * @return a pretty print representation of the offset
     */
    override fun toString(): String {
        return "[$x, $y, $z]"
    }
}