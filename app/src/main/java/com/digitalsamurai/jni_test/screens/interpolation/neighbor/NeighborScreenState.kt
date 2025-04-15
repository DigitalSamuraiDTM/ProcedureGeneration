package com.digitalsamurai.jni_test.screens.interpolation.neighbor

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.BitmapRenderer

data class NeighborScreenState(
    val bitmapRendererState: BitmapRenderer.State,
) : UiState()