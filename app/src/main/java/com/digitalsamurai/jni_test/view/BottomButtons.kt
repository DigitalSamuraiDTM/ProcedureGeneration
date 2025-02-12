package com.digitalsamurai.jni_test.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.jni_test.theme.AppTheme
import com.digitalsamurai.jni_test.theme.kotlinButtonBackground
import com.digitalsamurai.jni_test.theme.ndkButtonBackground

@Composable
public fun NdkButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.ndkButtonBackground,
            )
    ) {
        Text("NDK")
    }
}

@Composable
public fun KotlinButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.kotlinButtonBackground,
            )
    ) {
        Text("Kotlin")
    }
}
// TODO мигрировать на кастомную палетту которая будет подбирать цвета в зависимости от выбранного режима

@Composable()
@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() {
    AppTheme {
        Surface {
            Row(modifier = Modifier.fillMaxSize().height(70.dp)) {
                NdkButton(modifier = Modifier.fillMaxWidth().weight(1f)) {}
                KotlinButton(modifier = Modifier.fillMaxWidth().weight(1f)) { }
            }
        }
    }
}