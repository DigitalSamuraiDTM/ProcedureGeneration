package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun AppTheme(mod: ThemeMod, content: @Composable () -> Unit): ColorScheme {
    val colorScheme = when (mod) {
        ThemeMod.NDK -> if (isSystemInDarkTheme()) darkNdkColorScheme else lightNdkColorScheme
        ThemeMod.KOTLIN -> if (isSystemInDarkTheme()) darkKotlinColorScheme else lightKotlinColorScheme
    }
    MaterialTheme(colorScheme = colorScheme) {
        content()
    }
    return colorScheme
}