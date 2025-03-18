package com.digitalsamurai.noises.interpolators.dimensionals.one

import com.digitalsamurai.noises.data.D2Point
import kotlin.math.abs

/**
 * Одномерная интерполяция методом ближайшего соседа
 *
 * Суть интерполяции заключается в том, что мы должны найти такую координату х в списке [point] которая будет БЛИЖАЙШЕЙ
 * для искомого [x] и вернуть значение Y по найденной ближайшей точки из списка.
 *
 * Проблема: при поиске ближайшей точки мы поймать ситуацию, когда для [x] подходят две точки и тогда придется принимать решение:
 * 1) выбрать случайно
 * 2) выбрать первую попавшуюся
 * 3) "усреднить" (тогда это не совсем метод ближайшего соседа, но тоже вариант)
 *
 */
internal fun nearestNeighborInterpolation(
    x: Float,
    point: Set<D2Point>,
): Float {
    return point.minBy { abs(it.x - x) }.y
}