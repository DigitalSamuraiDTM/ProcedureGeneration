package com.digitalsamurai.monochrome

import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.interpolators.dimensionals.one.linearInterpolation
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LinearInterpolatorTest {
    @Test
    fun testLinearInterpolator() {
        // прямая ox = oy
        val x = 10f
        val y = linearInterpolation(
            x = x,
            point1 = D2Point(0.0f, 0.0f),
            point2 = D2Point(11f, 11f)
        )
        println(y)
        assert(y == 10f)
    }

}