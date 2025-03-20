package com.digitalsamurai.monochrome

import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.interpolators.Interpolation
import org.junit.Test

class NearestInterpolationTest {
    @Test
    fun oneDimensional() {
        // прямая ox = oy
        val x = 5f
        val points = buildSet {
            add(D2Point(0f, 0f))
            add(D2Point(1f, 1f))
            add(D2Point(2f, 2f))
            add(D2Point(3f, 3f))
            add(D2Point(4f, 4f))
            add(D2Point(7f, 7f))
            add(D2Point(8f, 8f))
        }
        val result = Interpolation.OneDimensional.nearestNeighbor(x, points)
        println(result)
        assert(result == 4f)

        val xNegative = -1f
        val resultNegative = Interpolation.OneDimensional.nearestNeighbor(xNegative, points)
        println(resultNegative)
        assert(resultNegative == 0f)
    }

}