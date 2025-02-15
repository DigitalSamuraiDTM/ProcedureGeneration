package com.digitalsamurai.jni_test.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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