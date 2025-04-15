package com.digitalsamurai.jni_test.screens.interpolation.linear

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.BitmapRenderer

data class LinearScreenState(
    val bitmapRendererState: BitmapRenderer.State,
) : UiState()