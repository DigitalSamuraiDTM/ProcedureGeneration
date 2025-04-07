package com.digitalsamurai.jni_test.view

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.jni_test.theme.AppTheme
import com.digitalsamurai.jni_test.theme.ThemeMod

object ImageSelector {

    @Composable
    operator fun invoke(
        state: State,
        modifier: Modifier = Modifier,
        onImageSelectClicked: () -> Unit,
        onImageDrop: () -> Unit,
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(2.dp)
        ) {

            if (state.image != null) {
                Image(
                    bitmap = ImageBitmap.imageResource(state.image),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Selected image"
                )
            } else {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable(true, onClick = onImageSelectClicked)) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.Center)
                    ) {
                        Image(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth()
                        )
                        Text(
                            "Select image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    data class State(
        @DrawableRes
        val image: Int?,
    )

    fun defaultState(): State = State(
        image = null
    )
}

@Composable
@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() {
    AppTheme(mod = ThemeMod.KOTLIN) {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                ImageSelector(
                    state = ImageSelector.defaultState(),
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.25f),
                    onImageSelectClicked = {},
                    onImageDrop = {}
                )
            }
        }
    }
}