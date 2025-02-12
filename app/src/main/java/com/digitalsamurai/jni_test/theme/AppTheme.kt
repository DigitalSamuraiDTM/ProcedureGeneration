package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun AppTheme(content: @Composable () -> Unit) {
    // todo override theme
    val colorScheme = if (isSystemInDarkTheme()) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    MaterialTheme(colorScheme = colorScheme) {
        content()
    }
}