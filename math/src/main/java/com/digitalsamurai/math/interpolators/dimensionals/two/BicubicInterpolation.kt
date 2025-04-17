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

internal fun bicubicInterpolation2(entryPoint: D2Point, pointsZValues: List<List<Float>>): Float {
    val x = entryPoint.x
    val y = entryPoint.y

    val b1 = 0.25f * (x - 1) * (x - 2) * (x + 1) * (y - 1) * (y - 2) * (y + 1)
    val b2 = -0.25f * x * (x + 1) * (x - 2) * (y - 1) * (y - 2) * (y + 1)
    val b3 = -0.25f * y * (x - 1) * (x - 2) * (x + 1) * (y + 1) * (y - 2)
    val b4 = 0.25f * x * y * (x + 1) * (x - 2) * (y + 1) * (y - 2)

    val b5 = -1.0f / 12 * x * (x - 1) * (x - 2) * (y - 1) * (y - 2) * (y + 1)
    val b6 = -1.0f / 12 * y * (x - 1) * (x - 2) * (x + 1) * (y - 1) * (y - 2)
    val b7 = 1.0f / 12 * x * y * (x - 1) * (x - 2) * (y + 1) * (y - 2)
    val b8 = 1.0f / 12 * x * y * (x + 1) * (x - 2) * (y - 1) * (y - 2)

    val b9 = -1.0f / 12 * x * (x - 1) * (x + 1) * (y - 1) * (y - 2) * (y + 1)
    val b10 = 1.0f / 12 * y * (x - 1) * (x - 2) * (x + 1) * (y - 1) * (y + 1)
    val b11 = 1.0f / 36 * x * y * (x - 1) * (x - 2) * (y - 1) * (y - 2)
    val b12 = -1.0f / 12 * x * y * (x - 1) * (x + 1) * (y + 1) * (y - 2)
    val b13 = -1.0f / 12 * x * y * (x + 1) * (x - 2) * (y - 1) * (y + 1)

    val b14 = -1.0f / 36 * x * y * (x - 1) * (x + 1) * (y - 1) * (y - 2)
    val b15 = -1.0f / 36 * x * y * (x - 1) * (x - 2) * (y - 1) * (y + 1)
    val b16 = 1.0f / 36 * x * y * (x - 1) * (x + 1) * (y - 1) * (y + 1)

    val out = b1 * pointsZValues[1][1] + b2 * pointsZValues[1][2] + b3 * pointsZValues[2][1] + b4 * pointsZValues[2][2] +
        + b5 * pointsZValues[1][0] + b6 * pointsZValues[0][1] + b7 * pointsZValues[2][0] + b8 * pointsZValues[0][2] +
            + b9 * pointsZValues[1][3] + b10 * pointsZValues[3][1] + b11 * pointsZValues[0][0] + b12 * pointsZValues[2][3] +
            + b13 * pointsZValues[3][2] + b14 * pointsZValues[0][3] * b15 * pointsZValues[3][0] + b16 * pointsZValues[3][3]

    return out
}
