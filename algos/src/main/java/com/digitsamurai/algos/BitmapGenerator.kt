package com.digitsamurai.algos

import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.WorkerThread
import androidx.core.graphics.set
import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.data.D3Point
import com.digitalsamurai.math.interpolators.Interpolation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.random.Random

// TODO: дело в том, что картинка считается по координатам с левого верхнего угла вниз, а интерполяция в декартовой системе считается с левого нижнего угла вниз
//  из-за этого картинка получаются перевернутыми в действительности. Надо это исправить

// TODO: билинейная интерполяция через матрицу может иметь погрешность вычислений в пиксель. Пример 1000х1000 разрешение матрицы 4
//  получаем расположение точек 0,333,666,999 == 1000 пиксель забыли(

class BitmapGenerator @Inject constructor() {

    private fun newBitmapTemplate(size: Size): Bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)

    @WorkerThread
    suspend fun bicubicBitmap(size: Size): Bitmap = with(Dispatchers.Default) {
        val matrix = mutableListOf<List<Color>>()
        repeat(4) {
            val line = mutableListOf<Color>()
            repeat(4) {
                line.add(randomColor())
            }
            matrix.add(line)
        }
        val bitmap = newBitmapTemplate(size)

        // надо вычитать !!!единицу!!! для того, чтобы интерполироваться от -1 до 2
        val bicubicScaleIndexWidth = 3f / bitmap.width
        val bicubicScaleIndexHeight = 3f / bitmap.height

        val redColors = matrix.map { it.map { color -> color.red() } }
        val greenColors = matrix.map { it.map { color -> color.green() } }
        val blueColors = matrix.map { it.map { color -> color.blue() } }

        repeat(bitmap.height) { y ->
            repeat(bitmap.width) { x ->
                val red = Interpolation.TwoDimensional.bicubic(
                    entryPoint = D2Point(
                        x = (x * 3f / bitmap.width) - 1,
                        y = (y * 3f / bitmap.height) - 1,
                    ),
                    pointsZValues = redColors
                )
                val green = Interpolation.TwoDimensional.bicubic(
                    entryPoint = D2Point(
                        x = (x * 3f / bitmap.width) - 1,
                        y = (y * 3f / bitmap.height) - 1,
                    ),
                    pointsZValues = greenColors
                )
                val blue = Interpolation.TwoDimensional.bicubic(
                    entryPoint = D2Point(
                        x = (x * 3f / bitmap.width) - 1,
                        y = (y * 3f / bitmap.height) - 1,
                    ),
                    pointsZValues = blueColors
                )
                bitmap.setPixel(x, y, Color.rgb(red, green, blue))
            }
        }
        return@with bitmap
    }

    @WorkerThread
    suspend fun bilinearBitmap(size: Size, bilinearConfig: BilinearConfig.Matrix): Bitmap = with(Dispatchers.Default) {
        val bitmap = newBitmapTemplate(size)
        val matrixColor = mutableListOf<List<Color>>()
        repeat(bilinearConfig.resolution) { h ->
            val row = mutableListOf<Color>()
            repeat(bilinearConfig.resolution) { w ->
                row.add(randomColor())
            }
            matrixColor.add(row)
        }
        // считаем ожидаемые размеры клеток, чтобы иметь возможность определять в какой клетке надо провести интерполяцию
        val ceilX = (bitmap.width / (bilinearConfig.resolution - 1)).toFloat()
        val ceilY = (bitmap.height / (bilinearConfig.resolution - 1)).toFloat()
        repeat(bitmap.height) { y ->
            repeat(bitmap.width) { x ->
                // находим клетку, в которой находится точка
                // ограничиваем погрешность делений вещественных чисел через coerseAtLeast, coerceAtMost.
                val currentX = (ceil(x / ceilX) - 1).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(bilinearConfig.resolution - 2)
                val currentY = (ceil(y / ceilY) - 1).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(bilinearConfig.resolution - 2)
                // находим цвета и интерполируем
                val ltColor = matrixColor[currentX][currentY]
                val rbColor = matrixColor[currentX + 1][currentY + 1]
                val rtColor = matrixColor[currentX + 1][currentY]
                val lbColor = matrixColor[currentX][currentY + 1]
                val color = bilinearWithColor(
                    entryPoint = D2Point(x.toFloat(), y.toFloat()),
                    colorLT = ColorPoint(color = ltColor, point = D2Point(currentX * ceilX, currentY * ceilY)),
                    colorLB = ColorPoint(color = lbColor, point = D2Point(currentX * ceilX, (currentY + 1) * ceilY)),
                    colorRB = ColorPoint(color = rbColor, point = D2Point((currentX + 1) * ceilX, (currentY + 1) * ceilY)),
                    colorRT = ColorPoint(color = rtColor, point = D2Point((currentX + 1) * ceilX, currentY * ceilY)),
                )
                bitmap[x, y] = color.toArgb()
            }
        }

        return@with bitmap
    }

    suspend fun bilinearBitmap(size: Size, bilinearConfig: BilinearConfig.FourPoints): Bitmap = withContext(Dispatchers.Default) {
        val bitmap = newBitmapTemplate(size)
        val colorLB = ColorPoint(point = D2Point(0f, 0f), color = bilinearConfig.colorLB)
        val colorRB = ColorPoint(point = D2Point(size.width.toFloat(), 0f), color = bilinearConfig.colorRB)
        val colorLT = ColorPoint(point = D2Point(0f, size.height.toFloat()), color = bilinearConfig.colorLT)
        val colorRT = ColorPoint(point = D2Point(size.width.toFloat(), size.height.toFloat()), color = bilinearConfig.colorRT)
        repeat(bitmap.height) { h ->
            repeat(bitmap.width) { w ->
                val color = bilinearWithColor(
                    entryPoint = D2Point(w.toFloat(), h.toFloat()),
                    colorLB, colorRB, colorLT, colorRT
                )
                bitmap.set(x = w, y = h, color = color.toArgb())
            }
        }
        return@withContext bitmap
    }

    private fun bilinearWithColor(
        entryPoint: D2Point,
        colorLB: ColorPoint,
        colorRB: ColorPoint,
        colorLT: ColorPoint,
        colorRT: ColorPoint,
    ): Color {
        val red = Interpolation.TwoDimensional.bilinear(
            entryPoint = entryPoint,
            pointLeftBottom = D3Point(colorLB.x, colorLB.y, colorLB.red()),
            pointRightBottom = D3Point(colorRB.x, colorRB.y, colorRB.red()),
            pointLeftTop = D3Point(colorLT.x, colorLT.y, colorLT.red()),
            pointRightTop = D3Point(colorRT.x, colorRT.y, colorRT.red()),
        )

        val green = Interpolation.TwoDimensional.bilinear(
            entryPoint = entryPoint,
            pointLeftBottom = D3Point(colorLB.x, colorLB.y, colorLB.green()),
            pointRightBottom = D3Point(colorRB.x, colorRB.y, colorRB.green()),
            pointLeftTop = D3Point(colorLT.x, colorLT.y, colorLT.green()),
            pointRightTop = D3Point(colorRT.x, colorRT.y, colorRT.green()),
        )

        val blue = Interpolation.TwoDimensional.bilinear(
            entryPoint = entryPoint,
            pointLeftBottom = D3Point(colorLB.x, colorLB.y, colorLB.blue()),
            pointRightBottom = D3Point(colorRB.x, colorRB.y, colorRB.blue()),
            pointLeftTop = D3Point(colorLT.x, colorLT.y, colorLT.blue()),
            pointRightTop = D3Point(colorRT.x, colorRT.y, colorRT.blue()),
        )
        return Color.valueOf(red, green, blue)
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
                    )] = randomColor()
                }
                points
            }
        }

        val bitmap = newBitmapTemplate(size)

        repeat(bitmap.width) { w ->
            repeat(bitmap.height) { h ->
                val nearestZ = Interpolation.TwoDimensional.nearestNeighbor(
                    entryPoint = D2Point(w.toFloat(), h.toFloat()),
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

    private fun randomColor(accuracy: Int = 100): Color {
        return Color.valueOf(
            Random.nextInt(accuracy) / accuracy.toFloat(),
            Random.nextInt(accuracy) / accuracy.toFloat(),
            Random.nextInt(accuracy) / accuracy.toFloat(),
        )
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

    sealed class BilinearConfig {
        data class FourPoints(
            val colorLB: Color,
            val colorRB: Color,
            val colorLT: Color,
            val colorRT: Color,
        )

        data class Matrix(
            val resolution: Int,
        )
    }

    private inner class ColorPoint(
        val point: D2Point,
        val color: Color,
    ) {
        fun red() = color.red()
        fun green() = color.green()
        fun blue() = color.blue()
        val x: Float get() = point.x
        val y: Float get() = point.y
    }
}