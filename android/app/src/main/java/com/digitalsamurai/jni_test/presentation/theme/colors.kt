package com.digitalsamurai.jni_test.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// primary  ocновной цвет бэкграунда элемента (например кнопки)
// surface - задает цвет основного фона для surface. Норм, если оборачивать весь контент в surface
// surfaceContainerHighest - цвет фона Card
// surface container - цвет фона bottom app bar
// onSecondaryColor - цвет иконки выбранного объекта в bottom app bar
// secondaryContainer - цвет контейнера выбранного объекта в bottom app bar

internal val lightNdkColorScheme = lightColorScheme(
    background = Color(0xFFF0FFF0),
    primary = Color(0xFF00751A),
    surface = Color(0xFFF0FFF0),
    surfaceContainer = Color(0xFFD5F3D5),
    onSecondaryContainer = Color(0xFF00751A),
    secondaryContainer = Color(0x00ff0000),
    surfaceContainerHighest = Color(0xFFD5F3D5),
    surfaceTint = Color.Red,
)

internal val darkNdkColorScheme = darkColorScheme(
    background = Color(0xFF2A342A),
    primary = Color(0xFF00E826),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF2A342A),
    surfaceContainer = Color(0xFF3E4B3E),
    onSecondaryContainer = Color(0xFF00E826),
    secondaryContainer = Color(0x00ff0000),
    surfaceContainerHighest = Color(0xFF3E4B3E),
)

internal val lightKotlinColorScheme = lightColorScheme(
    background = Color(0xFFFCF2FF),
    primary = Color(0xFF4A0069),
    surface = Color(0xFFFCF2FF),
    surfaceContainer = Color(0xFFEBD5F3),
    onSecondaryContainer = Color(0xFF4A0069),
    secondaryContainer = Color(0x00ff0000),
    surfaceContainerHighest = Color(0xFFEBD5F3),
)

internal val darkKotlinColorScheme = darkColorScheme(
    background = Color(0xFF312A34),
    primary = Color(0xFFAA00E8),
    primaryContainer = Color(0xFF2A2A2A),
    surface = Color(0xFF312A34),
    surfaceContainer = Color(0xFF483E4B),
    onSecondaryContainer = Color(0xFFAA00E8),
    secondaryContainer = Color(0x00ff0000),
    surfaceContainerHighest = Color(0xFF483E4B),
)