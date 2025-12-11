package com.digitalsamurai.jni_test.presentation.screens.interpolation.linear

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer

data class LinearScreenState(
    val bitmapRendererState: BitmapRenderer.State,
) : UiState()