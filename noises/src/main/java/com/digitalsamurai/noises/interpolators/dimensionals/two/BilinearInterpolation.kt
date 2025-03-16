package com.digitalsamurai.noises.interpolators.dimensionals.two

import com.digitalsamurai.noises.data.D2Point
import com.digitalsamurai.noises.data.D3Point

/**
 * Билинейная интерполяция это фактически двумерная линейная интерполяция
 * Задача: есть входная точка [entryPoint]. Есть 4 точки, которые образуют линейчатую поверхность (очередность точек важна)
 * Надо вернуть результат интерполяции (в представлении это будет координата Z - расположение [entryPoint] на этой самой плоскости)
 *
 * ВАЖНО:
 * Важным условием билинейной интерполяции является то, что точки плоскости (плоскости x0y) должны образовывать прямоугольник параллельный осям x0y:
 * Для координат z (то есть для f(x,y)) такого условия может и не быть, выбираем как угодно и от этого зависит итоговая глубина (если так можно назвать) интерполяции
 *
 */
internal fun bilinearInterpolation(
    entryPoint: D2Point,
    pointLeftBottom: D3Point,
    pointRightBottom: D3Point,
    pointLeftTop: D3Point,
    pointRightTop: D3Point,
): Float {
    val x1 = if (pointLeftBottom.x == pointLeftTop.x) pointLeftBottom.x else {
        throw IllegalStateException("coordinates of left points must be parallel 0Y")
    }
    val x2 = if (pointRightBottom.x == pointRightTop.x) pointRightBottom.x else {
        throw IllegalStateException("coordinates of right points must be parallel 0Y")
    }
    val y1 = if (pointLeftBottom.y == pointRightBottom.y) pointLeftBottom.y else {
        throw IllegalStateException("coordinates of bottom points must be parallel 0X")
    }
    val y2 = if (pointLeftTop.y == pointRightTop.y) pointLeftTop.y else {
        throw IllegalStateException("coordinates of top points must be parallel 0X")
    }
    // интерполяции по z на отрезке x2-x1 для нижнего ребра
    val zR1 = (x2 - entryPoint.x) / (x2 - x1) * pointLeftBottom.z + (entryPoint.x - x1) / (x2 - x1) * pointRightBottom.z
    // интерполяции по z на отрезке x2-x1 для верхнего ребра
    val zR2 = (x2 - entryPoint.x) / (x2 - x1) * pointLeftTop.z + (entryPoint.x - x1) / (x2 - x1) * pointRightTop.z

    // получили две z координаты лежащие на отрезках x1:leftBottom.z -- x2:rightBottom.z и x1:leftTop.z -- x2:rightTop.z
    // теперь надо интерполировать эти координаты вместе с ребрами y, чтобы получить интерполяцию искомой точки на z
    return (y2 - entryPoint.y) / (y2 - y1) * zR1 + (entryPoint.y - y1) / (y2 - y1) * zR2
}