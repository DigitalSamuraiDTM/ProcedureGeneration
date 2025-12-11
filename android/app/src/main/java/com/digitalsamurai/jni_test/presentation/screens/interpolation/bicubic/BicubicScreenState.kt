package com.digitalsamurai.jni_test.presentation.screens.interpolation.bicubic

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer

data class BicubicScreenState(
    val bitmapRendererState: BitmapRenderer.State,
) : UiState()