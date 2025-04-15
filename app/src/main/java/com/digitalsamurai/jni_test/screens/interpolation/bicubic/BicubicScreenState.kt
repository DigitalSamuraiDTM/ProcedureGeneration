package com.digitalsamurai.jni_test.screens.interpolation.bicubic

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.BitmapRenderer

data class BicubicScreenState(
    val bitmapRendererState: BitmapRenderer.State,
) : UiState()