package com.digitalsamurai.jni_test.data.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import com.digitalsamurai.core.otel.extensions.withTracedContext
import com.digitsamurai.utils.extensions.generateName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlin.collections.set

@WorkerThread
class BitmapRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val storage = context.getDir(STORAGE_NAME, Context.MODE_PRIVATE)

    private val cache = ConcurrentHashMap<String, Bitmap>()

    sealed class Name {
        data object Auto : Name()
        data class Value(val id: String) : Name()
    }

    suspend fun set(bitmap: Bitmap, id: Name = Name.Auto): Boolean = withTracedContext("BitmapRepository.set") {
        val fileName = when (id) {
            Name.Auto -> bitmap.generateName()
            is Name.Value -> id.id
        } + PNG_EXTENSION
        val file = File(storage, fileName)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())
            .also { cache[fileName] = bitmap }
    }

    fun delete(id: String): Boolean {
        val file = File(storage, id)
        return file.delete().also { cache.remove(id) }
    }

    fun get(id: String): Bitmap {
        // без синхронизации может не быть попадания в кеш, если читать один файл двумя потоками, так как чтение файла неатомарно, но
        // синхронизация будет хуже, так как будет синхронизация чтения с диска, а эт кабздец
        // поэтому мне проще случайно считать один и тот же файл с диска два раза
        if (cache[id] != null) return cache[id]!!
        val file = File(storage, id)
        val bitmap = BitmapFactory.decodeStream(file.inputStream())
        return bitmap.also { cache[id] = it }
    }

    fun get(name: Name.Value): Bitmap = get(name.id)

    /**
     * file path , bitmap id
     */
    fun getMetaInfo(): List<Pair<String, String>> {
        val storage = context.getDir(STORAGE_NAME, Context.MODE_PRIVATE)
        return storage.listFiles()?.map { file -> Pair(file.path, file.name) } ?: emptyList()
    }

    private companion object {
        const val STORAGE_NAME = "bitmaps"
        const val PNG_EXTENSION = ".png"
    }
}