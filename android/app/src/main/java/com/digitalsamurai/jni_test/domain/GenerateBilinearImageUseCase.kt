package com.digitalsamurai.jni_test.domain

import android.graphics.Bitmap
import com.digitalsamurai.core.otel.extensions.withTracedContext
import com.digitsamurai.algos.BitmapGenerator
import javax.inject.Inject

class GenerateBilinearImageUseCase @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) {
    suspend operator fun invoke(
        size: BitmapGenerator.Size,
        bilinearConfig: BitmapGenerator.BilinearConfig.Matrix,
    ): Bitmap = withTracedContext("GenerateBilinearImageUseCase.invoke") {
        bitmapGenerator.bilinearBitmap(size, bilinearConfig)
    }
}