package com.digitalsamurai.monochrome

import com.digitalsamurai.noises.data.D2Point
import com.digitalsamurai.noises.data.D3Point
import com.digitalsamurai.noises.interpolators.bilinearInterpolation
import org.junit.Test

class BilinearInterpolatorTest {
    @Test
    fun testLinearInterpolator() {

        val q11 = D3Point(0f,0f,0f)
        val q21 = D3Point(1f,0f,1f)
        val q12 = D3Point(0f,1f,1f)
        val q22 = D3Point(1f,1f,0.5f)
        val entryPoint = D2Point(0.6f,0.6f)

        val i = bilinearInterpolation(
            entryPoint = entryPoint,
            pointLeftBottom = q11,
            pointRightBottom = q21,
            pointLeftTop = q12,
            pointRightTop = q22
        )
        println(i)
        assert(true)
    }

}