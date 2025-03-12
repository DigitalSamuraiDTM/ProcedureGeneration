package com.digitalsamurai.noises.interpolators

import com.digitalsamurai.noises.data.D2Point
import com.digitalsamurai.noises.data.D3Point

/**
 * Билинейная интерполяция это фактически двумерная линейная интерполяция
 * Задача: есть входная точка [entryPoint]. Есть 4 точки, которые образуют линейчатую поверхность (очередность точек важна)
 * Надо вернуть результат интерполяции (в представлении это будет координата Z)
 *
 * ВАЖНО:
 * Важным условием билинейной интерполяции является то, что точки плоскости (плоскости x0y ) должны образовывать отрезки находящиеся НЕ под углом относительно осей координат:
 * То есть [pointLeftBottom] как левая нижняя точка и [pointRightBottom] как правая нижняя точка должны образовать отрезок параллельный оси 0x
 * То есть [pointLeftTop] как левая верхняя точка и [pointRightTop] как правая верхняя точка должны образовать отрезок параллельный оси 0x
 * Аналогично относительно оси ординат точки для: [pointRightBottom] + [pointRightTop], [pointLeftBottom]+[pointLeftTop]
 * Для координат z (то есть для f(x,y)) такого условия может и не быть, выбираем как угодно и от этого зависит итоговая глубина (если так можно назвать) интерполяции
 *
 */
public fun bilinearInterpolation(
    entryPoint: D2Point,
    pointLeftBottom: D3Point,
    pointRightBottom: D3Point,
    pointLeftTop: D3Point,
    pointRightTop: D3Point,
): Float {

}