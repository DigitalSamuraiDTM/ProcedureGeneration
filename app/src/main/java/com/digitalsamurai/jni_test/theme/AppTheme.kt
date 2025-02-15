package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.digitalsamurai.jni_test.ThemeMod

@Composable
internal fun AppTheme(mod: ThemeMod, content: @Composable () -> Unit) {
    val colorScheme = when(mod) {
        ThemeMod.NDK -> if (isSystemInDarkTheme()) darkNdkColorScheme else lightNdkColorScheme
        ThemeMod.KOTLIN -> if (isSystemInDarkTheme()) darkKotlinColorScheme else lightKotlinColorScheme
    }
    MaterialTheme(colorScheme = colorScheme) {
        content()
    }
}