package com.digitalsamurai.jni_test.view

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.jni_test.theme.AppTheme
import com.digitalsamurai.jni_test.theme.ThemeMod
import com.digitalsamurai.jni_test.theme.kotlinButtonBackground
import com.digitalsamurai.jni_test.theme.ndkButtonBackground

object ModConverter {

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        state: State,
        onModSelected: (State.Mod) -> Unit,
    ) {
        val ndkWidthWeight: Float by animateFloatAsState(if (state.selectedMod == State.Mod.NDK || state.selectedMod == null) 1f else 0.4f)
        val kotlinWidthWeight: Float by animateFloatAsState(if (state.selectedMod == State.Mod.KOTLIN || state.selectedMod == null) 1f else 0.4f)
        Row(modifier = modifier) {
            NdkButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(ndkWidthWeight)
            ) {
                onModSelected(State.Mod.NDK)
            }
            Spacer(modifier = Modifier.width(8.dp))
            KotlinButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(kotlinWidthWeight)
            ) {
                onModSelected(State.Mod.KOTLIN)
            }
        }
    }

    data class State(
        val selectedMod: Mod?,
    ) {
        enum class Mod {
            NDK, KOTLIN
        }
    }

    fun defaultState(): State = State(null)
}

@Composable
fun NdkButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.ndkButtonBackground,
                contentColor = MaterialTheme.colorScheme.surface
            )
    ) {
        Text("NDK")
    }
}

@Composable
fun KotlinButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.kotlinButtonBackground,
                contentColor = MaterialTheme.colorScheme.surface
            )
    ) {
        Text("Kotlin")
    }
}
// TODO мигрировать на кастомную палетту которая будет подбирать цвета в зависимости от выбранного режима

@Composable()
@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() {
    AppTheme(mod = ThemeMod.NDK) {
        val state = remember { mutableStateOf(ModConverter.defaultState()) }
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                ModConverter(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxWidth(0.08f),
                    state = state.value,
                    { mod ->
                        state.value = state.value.copy(selectedMod = mod)
                    }
                )
            }
        }
    }
}