package com.digitalsamurai.noises.interpolators

import com.digitalsamurai.noises.data.D2Point
import com.digitalsamurai.noises.data.D3Point
import com.digitalsamurai.noises.interpolators.dimensionals.one.linearInterpolation
import com.digitalsamurai.noises.interpolators.dimensionals.two.bilinearInterpolation

public object Interpolation {

    public object OneDimensional {
        fun linear(
            x: Float,
            point1: D2Point,
            point2: D2Point,
        ): Float = linearInterpolation(
            x = x,
            point1 = point1,
            point2 = point2
        )
    }

    public object TwoDimensional {
        public fun bilinear(
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
    }
}