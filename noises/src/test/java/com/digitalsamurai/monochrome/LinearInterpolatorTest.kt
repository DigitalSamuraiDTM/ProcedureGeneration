package com.digitalsamurai.monochrome

import android.animation.TimeInterpolator
import android.view.animation.BaseInterpolator
import com.digitalsamurai.noises.data.Point
import com.digitalsamurai.noises.interpolators.linearInterpolation
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
            point1 = Point(0.0f, 0.0f),
            point2 = Point(11f, 11f)
        )
        println(y)
        assert(y == 10f)
    }

}