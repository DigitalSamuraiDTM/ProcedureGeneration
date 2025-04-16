package com.digitalsamurai.math.interpolators.dimensionals.two

import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.interpolators.dimensionals.one.cubicInterpolation

/**
 * Бикубическая интерполяция
 */
internal fun bicubicInterpolation(entryPoint: D2Point, pointsZValues: List<List<Float>>): Float {
    if (pointsZValues.size != 4) throw IllegalArgumentException("points matrix must be 4x4")
    val pointMinusOne = cubicInterpolation(
        entryX = entryPoint.x,
        yM1 = pointsZValues[0][0],
        y0 = pointsZValues[0][1],
        y1 = pointsZValues[0][2],
        y2 = pointsZValues[0][3],
    )

    val pointZero = cubicInterpolation(
        entryX = entryPoint.x,
        yM1 = pointsZValues[1][0],
        y0 = pointsZValues[1][1],
        y1 = pointsZValues[1][2],
        y2 = pointsZValues[1][3],
    )

    val pointOne = cubicInterpolation(
        entryX = entryPoint.x,
        yM1 = pointsZValues[2][0],
        y0 = pointsZValues[2][1],
        y1 = pointsZValues[2][2],
        y2 = pointsZValues[2][3],
    )

    val pointTwo = cubicInterpolation(
        entryX = entryPoint.x,
        yM1 = pointsZValues[3][0],
        y0 = pointsZValues[3][1],
        y1 = pointsZValues[3][2],
        y2 = pointsZValues[3][3],
    )

    val out = cubicInterpolation(
        entryX = entryPoint.y,
        yM1 = pointMinusOne,
        y0 = pointZero,
        y1 = pointOne,
        y2 = pointTwo
    )
    return out
}
