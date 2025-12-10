package com.digitsamurai.utils.extensions

import android.graphics.Bitmap

public fun Bitmap.generateName(): String {
    return "bitmap_${generationId}_${System.currentTimeMillis()}"
}