package com.digitalsamurai.jni_test.screens.interpolation.linear

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class LinearScreenEvent : UiEvent() {
    sealed class BitmapSaving : LinearScreenEvent() {
        class Success(val fileName: String) : LinearScreenEvent()
        data object Failed : LinearScreenEvent()
    }
}
