package com.digitsamurai.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File


fun Context.loadBitmapFromStorage(filePath: String): Bitmap {
    val file = File(filePath)
    val bitmap = BitmapFactory.decodeStream(file.inputStream())
    return bitmap
}