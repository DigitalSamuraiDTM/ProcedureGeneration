package com.digitalsamurai.monochrome

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.core.graphics.set
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlin.Byte

public class JavaMonochromeConverter {

    @OptIn(ExperimentalStdlibApi::class)
    fun makeImageMonochrome(context: Context, uri: Uri): Flow<ConvertingProcess> {
        return flow {
            context.contentResolver.openInputStream(uri)?.let { stream ->
                val factoryOptions = BitmapFactory.Options()
                factoryOptions.inMutable = true
                val bitmap = BitmapFactory.decodeStream(stream, null, factoryOptions)

                stream.close()

                if (bitmap == null) {
                    emit(ConvertingProcess(null,true, "BITMAP NULL"))
                    return@let null
                }
                if (!bitmap.isMutable) {
                    emit(ConvertingProcess(null,true, "BITMAP INMUTABLE"))
                    return@let null
                }

                bitmap
            }?.let { bitmap ->
                inProgress(bitmap)

                for (y: Int in 0 until bitmap.height) {
                    for (x: Int in 0 until bitmap.width) {
                        bitmap.set(x = x,y = y, color = Color.GREEN)
                        inProgress(bitmap)
                    }
                }
                emit(ConvertingProcess(bitmap,true, null))
            }
        }
    }


    private suspend fun FlowCollector<ConvertingProcess>.inProgress(bitmap: Bitmap) {
        emit(ConvertingProcess(bitmap, false, null))
    }
}
/** А ЧЕ ЭТО ВАШЕ ООП ЧЕРЕЗ СТЕЙТЫ ГОНЯТЬ ОШИБКУ ИЛИ УСПЕХ
* Я КАК ЭКСПЕРТ ПО GO РЕШИЛ ПОВТОРИТЬ ЛУЧШИЕ ПРАКТИКИ И ПРИМЕНЯЮ СЕКРЕТНЫЕ ПОДХОДЫ ВРЕМЕН РСФСР (НЕ ДЕЛАТЬ ТАК В РЕАЛЬНОЙ АНДРОИД РАЗРАБОТКЕ, А ТО ДРУГИЕ РАЗРАБЫ ТЕБЯ СОЖРУТ, ТАК КАК ЭТО НЕ ПАТТЕРН КОТЛИНА, ЮЗАЙ НОРМАЛЬНЫЙ ПАТТЕРН СТЕЙТОВ)
 * ЕСЛИ [errorReason] null И [isFinished] TRUE ТО СЧИТАЙ, ЧТО УСПЕШНО КОНВЕРТИРУЕМСЯ (И МЫ УВЕРЕНЫ, ЧТО ОБЪЕКТ ВСЕГДА СУЩЕСТВУЕТ И МОЖЕМ КАСТАНУТЬ requireNotNull(bitmap))
 * А ИНАЧЕ ЕСЛИ [isFinished] TRUE и [errorReason] != null ТО ЧЕТ ГДЕ-ТО ОШИБЛИСЬ
 **/
public data class ConvertingProcess (
    public val bitmap: Bitmap?,
    public val isFinished: Boolean,
    public val errorReason: String?
)