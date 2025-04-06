package com.digitalsamurai.jni_test.screens.linear

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class LinearScreenEvent : UiEvent() {
    sealed class BitmapSaving: LinearScreenEvent() {
        class Success(val fileName: String): LinearScreenEvent()
        object Failed: LinearScreenEvent()
    }
}
