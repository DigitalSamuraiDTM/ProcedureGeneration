package com.digitalsamurai.jni_test.presentation.view.items

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.digitalsamurai.jni_test.core.modifier.tracedClickable
import com.digitalsamurai.jni_test.presentation.theme.AppTheme
import com.digitalsamurai.jni_test.presentation.theme.ThemeMod

object FeatureItem {

    data class State(
        val id: String,
        val title: String,
        val icon: Bitmap?,
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        state: State,
        onClick: () -> Unit,
    ) {
        Card(modifier = modifier.tracedClickable("Tile") { onClick() }) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (state.icon == null) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = state.title, modifier = Modifier.weight(2f))
                } else {
                    Text("No image")
                    //TODO add image
                }
                Text(
                    modifier = Modifier.weight(1.5f),
                    text = state.title,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    lineHeight = TextUnit(1f, TextUnitType.Em),
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme(mod = ThemeMod.KOTLIN) {
        Surface {
            Box(modifier = Modifier.fillMaxSize())
            FeatureItem(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(90.dp),
                state = FeatureItem.State(
                    id = "exampleId",
                    title = "Linear interpolation",
                    icon = null,
                )
            ) {}
        }
    }
}