/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * DrawCircle.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.actions.internal

import me.alexirving.core.EngineManager
import me.alexirving.core.actions.Action
import me.alexirving.core.actions.ActionManager
import me.alexirving.core.actions.SuperAction
import me.alexirving.core.animation.objects.Offset
import me.alexirving.core.animation.objects.Offset.Companion.add
import me.alexirving.core.animation.objects.Offset.Companion.cross
import me.alexirving.core.animation.objects.Offset.Companion.normalize
import me.alexirving.core.animation.objects.Offset.Companion.scale
import me.alexirving.core.exceptions.CompileError
import me.alexirving.core.utils.loc
import me.alexirving.core.utils.pq
import java.text.DecimalFormat
import kotlin.math.cos
import kotlin.math.sin


class DrawCircle(manager: EngineManager, args: Map<String, String>, start: Int) : SuperAction(
    args, start
) {

    private val df = DecimalFormat("#.#")

    init {
        df.maximumFractionDigits = 10
    }

    override fun build(): MutableMap<Int, Action> {
        val frameChanges = mutableMapOf<Int, Action>()
        val origin = (args["origin"] as Map<String, Double>).loc()
        val degreesOfRotation = args["angle"] as Double
        val numberOfFrames = args["frameCount"] as Int
        val angleOfPlane = Math.toRadians(degreesOfRotation)
        val currentX = origin.x
        val currentY = origin.y
        val currentZ = origin.z


        //draw a vector from the origin to the current point to use as our radius vector
        val radiusVector = origin.clone()
        //project that vector onto the xz plane and take their cross product to get the vector perpendicular to the radius on the xz plane (N0)
        //use N0 and N90 as unit vectors to define the span of all possible vectors perpendicular to the radius
        //project that vector onto the xz plane and take their cross product to get the vector perpendicular to the radius on the xz plane (N0)
        //use N0 and N90 as unit vectors to define the span of all possible vectors perpendicular to the radius
        val radiusOnXZPlane = Offset(radiusVector.x, 0.0, radiusVector.z)
        val normal0 = cross(radiusOnXZPlane, radiusVector)
        val normal90 = cross(radiusVector, normal0)
        normal0.normalize()
        normal90.normalize()

        //create the unit vectors of the plane of the circle, where the first axis of the plane is the radius vector normalized,
        // and the second axis is a perpendicular normal vector such that the plane defined by these two vectors is at some angle theta+90 to the xz plane
        // (where theta=0 would be a circle that is perpendicular to the xz plane)

        //create the unit vectors of the plane of the circle, where the first axis of the plane is the radius vector normalized,
        // and the second axis is a perpendicular normal vector such that the plane defined by these two vectors is at some angle theta+90 to the xz plane
        // (where theta=0 would be a circle that is perpendicular to the xz plane)
        val unitVectorR = normalize(radiusVector)
        val unitVectorA = add(scale(normal0, cos(angleOfPlane)), scale(normal90, sin(angleOfPlane)))

        //create variables for calculating the circle

        //create variables for calculating the circle
        val incrementThetaBy = Math.toRadians(degreesOfRotation / numberOfFrames)
        var currentTheta = incrementThetaBy
        val radiusLength = radiusVector.length()
        var currentPoint: Offset

        //scale the unit vectors, multiply by the length and respective trig function, add them, and add the origin vector to the result for each frame

        //scale the unit vectors, multiply by the length and respective trig function, add them, and add the origin vector to the result for each frame
        for (i in 0 until numberOfFrames) {
            unitVectorR.pq(4)
            unitVectorA.pq(3)

            currentPoint = add(
                scale(unitVectorR, radiusLength * cos(currentTheta)),
                scale(unitVectorA, radiusLength * sin(currentTheta))
            )
            currentPoint.pq(1)
            origin.pq(2)
            currentPoint.add(origin)

            frameChanges[i] = ActionManager.compileAction((args["action"] as Map<String, Any>).toMutableMap().apply {
                currentPoint.pq("CURRENT")
                val loc = (this["location"] as Map<String, String>).toMutableMap()
                loc["x"] = currentPoint.x.toString()
                loc["y"] = currentPoint.y.toString()
                loc["z"] = currentPoint.x.toString()
                loc.pq("NUMBER A")
                this["location"] = loc.mapValues { it.value.toDouble() }
                this.pq("NUMBERS")
            }) ?: throw CompileError("Compile issues m8!")
            //increment theta
            currentTheta += incrementThetaBy
        }
        return frameChanges
    }


}


