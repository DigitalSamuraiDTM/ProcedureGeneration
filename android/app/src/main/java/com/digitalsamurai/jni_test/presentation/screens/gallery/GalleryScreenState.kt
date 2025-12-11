package com.digitalsamurai.jni_test.presentation.screens.gallery

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer

data class GalleryScreenState(
    val bitmapItems: List<BitmapRenderer.State>,
) : UiState()