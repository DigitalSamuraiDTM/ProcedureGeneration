package com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer

data class NeighborScreenState(
    val bitmapRendererState: BitmapRenderer.State,
    val isButtonLoading: Boolean,
) : UiState()