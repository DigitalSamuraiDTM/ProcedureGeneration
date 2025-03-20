package com.digitalsamurai.jni_test.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.jni_test.ThemeMod
import com.digitalsamurai.jni_test.theme.AppTheme

object SliderWithEdit {

    data class State(
        val current: Float,
    )

    @Composable
    operator fun invoke(modifier: Modifier = Modifier, state: State, onValueChanged: (Float) -> Unit) {
        Row {
            Slider(
                modifier = Modifier.weight(1f),
                value = state.current,
                valueRange = MIN_VALUE..MAX_VALUE,
                onValueChange = onValueChanged
            )
            TextField(
                modifier = Modifier.weight(1f),
                value = state.current.toString(),
                onValueChange = { text -> onValueChanged(text.toFloat()) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }
    }

    private const val MIN_VALUE = 100f
    private const val MAX_VALUE = 1000f
}


@Composable
@Preview
private fun Preview() {
    AppTheme(mod = ThemeMod.KOTLIN) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                SliderWithEdit(
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(30.dp),
                    state = SliderWithEdit.State(550f)
                ) { v ->

                }
            }
        }
    }
}