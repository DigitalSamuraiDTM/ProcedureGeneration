package com.digitalsamurai.math.interpolators

import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.data.D3Point
import com.digitalsamurai.math.interpolators.dimensionals.one.cubicInterpolation
import com.digitalsamurai.math.interpolators.dimensionals.one.linearInterpolation
import com.digitalsamurai.math.interpolators.dimensionals.one.nearestNeighborInterpolation
import com.digitalsamurai.math.interpolators.dimensionals.two.bicubicInterpolation
import com.digitalsamurai.math.interpolators.dimensionals.two.bicubicInterpolation2
import com.digitalsamurai.math.interpolators.dimensionals.two.bilinearInterpolation

object Interpolation {

    object OneDimensional {
        fun linear(
            x: Float,
            point1: D2Point,
            point2: D2Point,
        ): Float = linearInterpolation(
            x = x,
            point1 = point1,
            point2 = point2
        )

        fun nearestNeighbor(
            x: Float,
            points: Set<D2Point>,
        ) = nearestNeighborInterpolation(
            x = x,
            point = points
        )

        fun cubic(
            x: Float,
            yM1: Float,
            y0: Float,
            y1: Float,
            y2: Float,
        ) = cubicInterpolation(
            entryX = x,
            yM1 = yM1,
            y0 = y0,
            y1 = y1,
            y2 = y2
        )
    }

    object TwoDimensional {
        fun bilinear(
            entryPoint: D2Point,
            pointLeftBottom: D3Point,
            pointRightBottom: D3Point,
            pointLeftTop: D3Point,
            pointRightTop: D3Point,
        ) = bilinearInterpolation(
            entryPoint = entryPoint,
            pointLeftBottom = pointLeftBottom,
            pointRightBottom = pointRightBottom,
            pointLeftTop = pointLeftTop,
            pointRightTop = pointRightTop,
        )

        fun nearestNeighbor(
            entryPoint: D2Point,
            points: Set<D3Point>,
        ) = com.digitalsamurai.math.interpolators.dimensionals.two.nearestNeighborInterpolation(
            entryPoint = entryPoint,
            points = points
        )

        fun bicubic(
            entryPoint: D2Point,
            pointsZValues: List<List<Float>>,
        ) = bicubicInterpolation(entryPoint, pointsZValues)
    }
}