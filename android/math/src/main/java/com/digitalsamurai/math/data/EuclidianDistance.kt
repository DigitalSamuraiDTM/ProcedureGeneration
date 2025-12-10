package com.digitalsamurai.math.data

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * В декартовых координатах евклидово расстояние это длина отрезка меж двух точек
 * Ищется теоремой пифагора :)
 */
internal infix fun D2Point.euclideanDistance(second: D2Point): Float {
    val xSide = abs(x - second.x)
    val ySide = abs(y - second.y)
    // сумма квадратов катетов равна квадрату гипотенузы
    return sqrt(xSide.pow(2) + ySide.pow(2))
}