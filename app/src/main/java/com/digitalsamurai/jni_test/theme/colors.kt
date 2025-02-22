package com.digitalsamurai.jni_test.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val lightNdkColorScheme = lightColorScheme(
    background = Color(0xFF006BEE),
    primary = Color(0xFF00751A),
    surface = Color(0xFFF0FFF0),
    surfaceContainerHighest = Color(0xFFD5F3D5),
    surfaceTint = Color.Red,
)
// primary  ocновной цвет бэкграунда элемента (например кнопки)
// surface - задает цвет основного фона для surface. Норм, если оборачивать весь контент в surface
// surfaceContainerHighest - цвет фона Card

internal val darkNdkColorScheme = darkColorScheme(
    background = Color(0xFFFF0000),
    primary = Color(0xFF00E826),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF2A342A),
    surfaceContainerHighest = Color(0xFF3E4B3E),
    )

internal val lightKotlinColorScheme = lightColorScheme(
    background = Color(0xFFEE0000),
    primary = Color(0xFF4A0069),
    surface = Color(0xFFFCF2FF),
    surfaceContainerHighest = Color(0xFFEBD5F3),
    )
// primary  ocновной цвет бэкграунда элемента (например кнопки)
// surface - задает цвет основного фона для surface. Норм, если оборачивать весь контент в surface

internal val darkKotlinColorScheme = darkColorScheme(
    background = Color(0xFFFF0000),
    primary = Color(0xFFAA00E8),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF312A34),
    surfaceContainerHighest = Color(0xFF483E4B),
    )