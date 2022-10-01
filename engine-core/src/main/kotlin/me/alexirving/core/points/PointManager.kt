/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * EcoManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.points

import me.alexirving.core.EngineManager
import me.alexirving.core.points.track.PointsTrack
import me.alexirving.core.utils.pq

class PointManager(private val m: EngineManager) {
    private val points = mutableMapOf<String, Points>()
    private val pointsTracks = mutableMapOf<String, PointsTrack>()
    fun create(id: String): Points {
        val e = Points(id, m)
        points[id] = e
        return e
    }

    fun register(points: Points) {
        if (points is PointsTrack)
            pointsTracks[points.id] = points
        else
            this.points[points.id] = points
    }

    fun delete(id: String) {
        points.remove(id)
    }

    fun getPoints(id: String) = points[id]
    fun getPointTrack(id: String) = pointsTracks[id]
    fun getPointsId() = points.keys
}