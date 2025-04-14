package com.digitalsamurai.jni_test.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BitmapRenderer {

    sealed interface State {
        val id: String

        // TODO: выпилить нахуй Empty State, картинка должна загружаться по-человечески и без пустот, вместо пустоты что-то другое показывать
        data object Empty : State {
            override val id: String = "empty"
        }

        data class ContentBitmap(
            val bitmap: Bitmap,
            override val id: String,
        ) : State

        data class ContentBitmapPath(
            override val id: String,
        ) : State

    }

    fun default(): State = State.Empty

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        state: State,
        onClick: (id: String) -> Unit,
        onBitmapIdLoad: ((path: String) -> Deferred<Bitmap>)? = null,
    ) {
        val loaderCoroutineScope = rememberCoroutineScope()
        Box(modifier = modifier.clickable { onClick(state.id) }) {
            when (state) {
                State.Empty -> {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }

                is State.ContentBitmap -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = state.bitmap.asImageBitmap(),
                        contentDescription = state.id
                    )
                }

                is State.ContentBitmapPath -> {
                    if (onBitmapIdLoad != null) {
                        val (bitmap, setBitmap) = remember { mutableStateOf<Bitmap?>(null) }
                        if (bitmap == null) {
                            val bitmapDeferred = onBitmapIdLoad.invoke(state.id)
                            LaunchedEffect(Unit) {
                                loaderCoroutineScope.launch(Dispatchers.Default) {
                                    setBitmap(bitmapDeferred.await())
                                }
                            }
                        }
                        if (bitmap != null) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = state.id
                            )
                        }
                    } else {
                        throw IllegalArgumentException("Need implement onBitmapIdLoad function")
                    }
                }
            }
        }
    }
}