///*
// * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
// * DrawSpiral.kt - is part of the McEngine!
// * Unauthorized copying of this file, via any medium is strictly prohibited
// * Proprietary and confidential
// * Written by Alex Irving <alexirving992@gmail.com>, day month year
// */
//package me.alexirving.core.animation.actions.actionables
//
//import me.alexirving.core.EngineManager
//import me.alexirving.core.animation.loader.AniCompiler
//import me.alexirving.core.animation.actions.Action
//import me.alexirving.core.animation.actions.SuperAction
//import me.alexirving.core.animation.utils.toLocation
//import java.text.DecimalFormat
//import java.util.regex.Pattern
//import kotlin.math.asin
//import kotlin.math.cos
//import kotlin.math.pow
//import kotlin.math.sin
//
//
//class DrawSpiral(manager: EngineManager, rawStatement: String, start: Int) : SuperAction(
//    manager, rawStatement, start
//){
//    private val pattern: Pattern =
//        Pattern.compile(".+\\((.+\\(.*\\));(\\[-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?]);(\\[-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?]);(-?\\d+(\\.\\d+)?);(\\d+);(\\d+)\\)")
//
//   private val df = DecimalFormat("#.#")
//
//    init {
//        df.maximumFractionDigits = 10
//    }
//
//    override fun build(): MutableMap<Int, Action> {
//        val args = pattern.matcher(raw)
//        if (!args.matches()) {
//            println("Error: Invalid DrawCircle statement \"$raw\"")
//            return mutableMapOf()
//        }
//        val a = mutableMapOf<Int, Action>()
//        val l = toLocation(args.group(2))!!
//        val l1 = toLocation(args.group(6))!!
//        val originX = l[0]
//        val originY = l[1]
//        var currentX = l1[0]
//        var currentY = l1[1]
//        val frameCount = args.group(12).toInt() //Total frames
//        // calculate r and starting theta
//        //sqrt( (x1-x2)^2 + (y1-y2)^2) )
//        val radiusLength = ((currentX - originX).pow(2.0) + (currentY - originY).pow(2.0)).pow(1.0 / 2)
//        // theta = sin^-1(currentX/radiusLength) (in radians)
//        val startingTheta = asin(currentX / radiusLength)
//        //determine increment value of each frame in radians
//        val incrementThetaBy = Math.toRadians(args.group(10).toDouble() / frameCount)
//        val reverseSpinDirection = false
//
//        if (!reverseSpinDirection) {
//
//            var currentTheta = startingTheta + incrementThetaBy
//            for (i in start until (frameCount + start)) {
//                //update x and y to be r*sin/cos(theta) and offset by the origins coordinates
//                currentX = radiusLength * sin(currentTheta) + originX
//                currentY = radiusLength * cos(currentTheta) + originY
//                //add coordinates to list
//
//                a[i] = AniCompiler.compileAction(
//                    m, args.group(1)
//                        .replace("X", df.format(currentX)).replace("Y", df.format(currentY))
//                )
//
//                // increment currentTheta
//                currentTheta += incrementThetaBy
//            }
//        } else {
//            //initialize the first frames theta value
//            var currentTheta = startingTheta - incrementThetaBy
//            for (i in start until frameCount + start) {
//                //update x and y to be r*sin/cos(theta) and offset by the origins coordinates
//                currentX = radiusLength * sin(currentTheta) + originX
//                currentY = radiusLength * cos(currentTheta) + originY
//                //add coordinates to list
//
//                a[i] = AniCompiler.compileAction(
//                    m, args.group(1)
//                        .replace("X", df.format(currentX)).replace("Y", df.format(currentY))
//                )
//                // increment currentTheta
//                currentTheta -= incrementThetaBy
//            }
//        }
//
////            a[current++] = AniCompiler.compileAction(pm, im, args.group(3)
////                .replace("X",currentX.toString())
////                .replace("Y",currentY.toString())
////                .replace("Z",currentZ.toString()))
//
//
//        return a
//    }
//}
//
//
