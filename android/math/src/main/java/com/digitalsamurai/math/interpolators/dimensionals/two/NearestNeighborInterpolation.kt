package com.digitalsamurai.math.interpolators.dimensionals.two

import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.data.D3Point
import com.digitalsamurai.math.data.euclideanDistance

/**
 * двумерная интерполяция методом ближайшего соседа
 * На самом деле не сильно отличается от одномерной, суть в том, что мы берем плоскость x0y и располагаем на ней [point]
 * Координата [D3Point.z] у [point] как и во всех интерполяциях будет искомой координатой для входной [entryPoint]
 * Собственно надо найти такую [D3Point] из множества [point] которая будет ближайшей. Координата z найденной [D3Point]
 * и будет результатом
 *
 * Проблемы: точно такие же, как и в случае одномерной интерполяции, Мы можем найти такую точку, у которой ближайшей будет
 * две точки. Тогда надо принимать волевое решение что делать. Смотри одномерную интерполяцию
 *
 * Почитать:
 * https://en.wikipedia.org/wiki/Nearest-neighbor_interpolation
 */
internal fun nearestNeighborInterpolation(
    entryPoint: D2Point,
    points: Set<D3Point>,
): Float {
    if (points.isEmpty()) {
        throw IllegalStateException("points is empty!")
    }
    var minDistance = -1f
    var result = -1f
    points.forEach { point ->
        val distance = entryPoint euclideanDistance point.toD2x0y()
        if (distance < minDistance || minDistance == -1f) {
            minDistance = distance
            result = point.z
        }
        // ищу ближайшую точку через евклидово расстояние. Проще говоря через гипотенузу прямоугольного треугольника
    }
    return result
}

