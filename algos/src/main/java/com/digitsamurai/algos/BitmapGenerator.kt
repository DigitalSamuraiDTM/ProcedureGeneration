package com.digitsamurai.algos

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.set
import com.digitalsamurai.math.data.D2Point
import com.digitalsamurai.math.data.D3Point
import com.digitalsamurai.math.interpolators.Interpolation
import javax.inject.Inject

class BitmapGenerator @Inject constructor() {

    suspend fun bilinearBitmap(size: Size, bilinearConfig: BilinearConfig): Bitmap {
        val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
        val colorLB = bilinearConfig.colorLB
        val colorRB = bilinearConfig.colorRB
        val colorLT = bilinearConfig.colorLT
        val colorRT = bilinearConfig.colorRT
        Log.d("OBAMA", "W: ${bitmap.width} H: ${bitmap.height}")
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


//                Log.d("OBAMA", "X: ${w} Y: ${h} COLOR: ${Color.valueOf(pixelColor.toInt())}")
                bitmap.set(
                    x = w,
                    y = h,
                    color = Color.valueOf(red, green, blue).toArgb()
                )
            }
        }
        return bitmap
    }


    data class Size(
        val width: Int,
        val height: Int,
    )

    data class BilinearConfig(
        val colorLB: Color,
        val colorRB: Color,
        val colorLT: Color,
        val colorRT: Color,
    )

}