package com.digitalsamurai.monochrome

import android.animation.TimeInterpolator
import android.view.animation.BaseInterpolator
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
        val fX = {x: Float -> x }
        val y = linearInterpolation(x, fX)
        println(y)
        assert(y == 10f)
    }

    @Test
    fun testAlphaAnimation() {
        // представим, что интерполируем alpha до 1.5f вьюшки за 400мс
        val animationTime = 400L
        val finishedAlpha = 1.5f
        val interpolationAlpha = { currentAnimationTime: Float -> (currentAnimationTime / animationTime) * finishedAlpha }
        // теперь можем брать каждую миллисекунду и получать по ней значение
        var currentAlpha = 0f
        val currentTime = System.currentTimeMillis()
        val destinationTime = currentTime + animationTime
        while (destinationTime >= System.currentTimeMillis()) {
            val animationTime = animationTime - (destinationTime - System.currentTimeMillis())
            currentAlpha = linearInterpolation(animationTime.toFloat(), interpolationAlpha)
            Thread.sleep(1)
            // можно заметить, что стоит знак >=
            println(currentAlpha)
        }

        val linearAnimationInterpolator = object: BaseInterpolator() {
            /**
             * [p0] значение между 0 и 1.0, где 0.0 это начало анимации, а 1.0 это конец анимации (оно потом умножится на длительность анимации)
             * @return интерполируемое значение
             */
            override fun getInterpolation(p0: Float): Float {
                return p0
            }

        }

        assert(true)
    }
}