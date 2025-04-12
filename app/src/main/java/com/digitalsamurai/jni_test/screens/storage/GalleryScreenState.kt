package com.digitalsamurai.jni_test.screens.storage

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.BitmapRenderer

data class GalleryScreenState(
    val bitmapItems: List<BitmapRenderer.State>,
) : UiState()