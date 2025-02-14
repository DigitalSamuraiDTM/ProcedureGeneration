package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal val lightColorScheme = lightColorScheme(
    background = Color(0xFFEE0000),
    primary = Color(0xFF00751A),
    surface = Color(0xFFEEEEEE)
)
// primary  ocновной цвет бэкграунда элемента (например кнопки)
// surface - задает цвет основного фона для surface. Норм, если оборачивать весь контент в surface

public val ColorScheme.ndkButtonBackground: Color
    @Composable get() =
        if (isSystemInDarkTheme()) {
            Color(0xFF00E826)
        } else {
            Color(0xFF00751A)
        }

public val ColorScheme.kotlinButtonBackground: Color
    @Composable get() =
        if (isSystemInDarkTheme()) {
            Color(0xFFAA00E8)
        } else {
            Color(0xFF4A0069)
        }

internal val darkColorScheme = darkColorScheme(
    background = Color(0xFFFF0000),
    primary = Color(0xFF00E826),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF313131),
)