package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal val lightColorScheme = lightColorScheme(
    background = Color(0xFFEE0000),
    primary = Color(0xFF005215),
    surface = Color(0xFFEEEEEE)
)
// primary  ocновной цвет бэкграунда элемента (например кнопки)
// surface - задает цвет основного фона для surface. Норм, если оборачивать весь контент в surface

public val ColorScheme.ndkButtonBackground: Color
    @Composable get() =
        if (isSystemInDarkTheme()) {
            Color(0xff81c784)
        } else {
            Color(0xff1b5e20)
        }

public val ColorScheme.kotlinButtonBackground: Color
    @Composable get() =
        if (isSystemInDarkTheme()) {
            Color(0xffba68c8)
        } else {
            Color(0xff4a148c)
        }

internal val darkColorScheme = darkColorScheme(
    background = Color(0xFFFF0000),
    primary = Color(0xFF00E826),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF313131),
)