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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource

object BitmapRenderer {

    sealed interface State {
        data object Empty: State
        data class Content(
            val bitmap: Bitmap,
            val id: String,
        ): State
    }

    fun default(): State = State.Empty

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        state: State,
        onClick: () -> Unit,
    ) {
        Box(modifier = modifier.clickable { onClick() }) {
            when(state) {
                State.Empty -> {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )

                }
                is State.Content -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = state.bitmap.asImageBitmap(),
                        contentDescription = state.id
                    )
                }
            }
        }
    }
}