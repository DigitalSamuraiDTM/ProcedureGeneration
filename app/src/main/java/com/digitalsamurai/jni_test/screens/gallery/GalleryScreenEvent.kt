package com.digitalsamurai.jni_test.screens.gallery

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class GalleryScreenEvent : UiEvent() {
    data object Error: GalleryScreenEvent()
}