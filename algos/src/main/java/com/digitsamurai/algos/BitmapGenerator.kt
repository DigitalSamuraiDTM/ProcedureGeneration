package com.digitsamurai.algos

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.set
import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.data.D3Point
import com.digitalsamurai.math.interpolators.Interpolation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

// TODO: дело в том, что картинка считается по координатам с левого верхнего угла вниз, а интерполяция в декартовой системе считается с левого нижнего угла вниз
//  из-за этого картинка получаются перевернутыми в действительности. Надо это исправить


class BitmapGenerator @Inject constructor() {

    private fun newBitmapTemplate(size: Size): Bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)

    suspend fun bilinearBitmap(size: Size, bilinearConfig: BilinearConfig): Bitmap = withContext(Dispatchers.Default) {
        val bitmap = newBitmapTemplate(size)
        val colorLB = bilinearConfig.colorLB
        val colorRB = bilinearConfig.colorRB
        val colorLT = bilinearConfig.colorLT
        val colorRT = bilinearConfig.colorRT
        repeat(bitmap.height) { h ->
            repeat(bitmap.width) { w ->

                val red = Interpolation.TwoDimensional.bilinear(
                    entryPoint = D2Point(w.toFloat(), h.toFloat()),
                    pointLeftBottom = D3Point(0f, 0f, colorLB.red()),
                    pointRightBottom = D3Point(size.width.toFloat(), 0f, colorRB.red()),
                    pointLeftTop = D3Point(0f, size.height.toFloat(), colorLT.red()),
                    pointRightTop = D3Point(size.width.toFloat(), size.height.toFloat(), colorRT.red()),
                )

                val green = Interpolation.TwoDimensional.bilinear(
                    entryPoint = D2Point(w.toFloat(), h.toFloat()),
                    pointLeftBottom = D3Point(0f, 0f, colorLB.green()),
                    pointRightBottom = D3Point(size.width.toFloat(), 0f, colorRB.green()),
                    pointLeftTop = D3Point(0f, size.height.toFloat(), colorLT.green()),
                    pointRightTop = D3Point(size.width.toFloat(), size.height.toFloat(), colorRT.green()),
                )

                val blue = Interpolation.TwoDimensional.bilinear(
                    entryPoint = D2Point(w.toFloat(), h.toFloat()),
                    pointLeftBottom = D3Point(0f, 0f, colorLB.blue()),
                    pointRightBottom = D3Point(size.width.toFloat(), 0f, colorRB.blue()),
                    pointLeftTop = D3Point(0f, size.height.toFloat(), colorLT.blue()),
                    pointRightTop = D3Point(size.width.toFloat(), size.height.toFloat(), colorRT.blue()),
                )
                bitmap.set(
                    x = w,
                    y = h,
                    color = Color.valueOf(red, green, blue).toArgb()
                )
            }
        }
        return@withContext bitmap
    }

    suspend fun neighborBitmap(size: Size, neighborConfig: NeighborConfig): Bitmap = withContext(Dispatchers.Default) {
        val entryPoints = when (neighborConfig) {
            is NeighborConfig.Random -> {
                val points = mutableMapOf<D3Point, Color>()
                repeat(neighborConfig.countPoints) { i ->
                    points[D3Point(
                        x = Random.nextInt(size.width).toFloat(), // TODO локации точек могут повторяться, это плохо
                        y = Random.nextInt(size.height).toFloat(),
                        z = i.toFloat()
                    )] = Color.valueOf(
                        Random.nextInt(100) / 100f,
                        Random.nextInt(100) / 100f,
                        Random.nextInt(100) / 100f,
                    )

                }
                points
            }
        }

        val bitmap = newBitmapTemplate(size)

        repeat(bitmap.width) { w ->
            repeat(bitmap.height) { h ->
                val nearestZ = Interpolation.TwoDimensional.nearestNeighbor(
                    entryPoint = D2Point(w.toFloat(),h.toFloat()),
                    points = entryPoints.keys
                )
                // используем z в качестве маркера, с помощью которого можно найти искомую точку, а по ее ключу достать цвет пикселя
                // это нужно, чтобы не интерполироваться отдельно по a,r,g,b
                val foundedColor = entryPoints[entryPoints.keys.first { it.z == nearestZ }] ?: throw IllegalStateException("Color was not founded")
                bitmap.set(
                    x = w,
                    y = h,
                    color = foundedColor.toArgb()
                )
            }
        }

        return@withContext bitmap
    }

    data class Size(
        val width: Int,
        val height: Int,
    )

    sealed class NeighborConfig {
        data class Random(
            val countPoints: Int,
        ) : NeighborConfig()
        // TODO ну кароче мануалочный конфиг надо сделать, где вводишь фикс количество цветов и еще один конфиг где прямо задаешь точки руками с цветами
    }

    data class BilinearConfig(
        val colorLB: Color,
        val colorRB: Color,
        val colorLT: Color,
        val colorRT: Color,
    )

}