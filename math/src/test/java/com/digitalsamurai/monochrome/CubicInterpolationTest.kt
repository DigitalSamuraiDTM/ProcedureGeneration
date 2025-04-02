package com.digitalsamurai.monochrome


import com.digitalsamurai.math.interpolators.dimensionals.one.cubicInterpolation
import org.junit.Test

class CubicInterpolationTest {
    @Test
    fun testCubicInterpolator() {
        // проверяем, что пересекаем точку 0
        val r1 = cubicInterpolation(
            entryX = 0f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r1)
        assert(r1 == 0f)
        // проверяем, что пересекаем точку -1
        val r2 = cubicInterpolation(
            entryX = -1f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r2)
        assert(r2 == -0.5f)

        // проверяем, что пересекаем точку 1
        val r3 = cubicInterpolation(
            entryX = 1f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r3)
        assert(r3 == 0.5f)

        // проверяем, что пересекаем точку 2
        val r4 = cubicInterpolation(
            entryX = 2f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r4)
        assert(r4 == -1f)


        val r5 = cubicInterpolation(
            entryX = -0.5f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r5)
        assert(r5 == -0.375f)

        val r6 = cubicInterpolation(
            entryX = 0.5f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r6)
        // погрешность, возвращается -0.37500003
        assert(r6 == 0.375f)

        val r7 = cubicInterpolation(
            entryX = 4f,
            yM1 = -0.5f,
            y0 = 0f,
            y1 = 0.5f,
            y2 = -1f
        )
        println(r7)
        assert(r7 == -18f)
    }


}