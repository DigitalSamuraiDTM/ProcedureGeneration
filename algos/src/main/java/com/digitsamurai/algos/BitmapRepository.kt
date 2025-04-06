package com.digitsamurai.algos

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.digitsamurai.utils.extensions.generateName
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@WorkerThread
class BitmapRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val storage = context.getDir(STORAGE_NAME, Context.MODE_PRIVATE)

    sealed class Name {
        data object Auto : Name()
        data class Value(val name: String) : Name()
    }

    fun set(bitmap: Bitmap, name: Name = Name.Auto): Boolean {
        val fileName = when (name) {
            Name.Auto -> bitmap.generateName()
            is Name.Value -> name.name
        }
        val file = File(storage, fileName)
        return bitmap.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())
    }

    fun delete(name: String): Boolean {
        val file = File(storage, name)
        return file.delete()
    }

    fun get(name: String): Bitmap {
        val file = File(storage, name)
        return TODO()
    }

    fun get(name: Name.Value): Bitmap? = get(name.name)

    fun getNames(): List<String> {
        val storage = context.getDir(STORAGE_NAME, Context.MODE_PRIVATE)
        return storage.list()?.toList() ?: emptyList()
    }

    private companion object {
        const val STORAGE_NAME = "bitmaps"
    }
}