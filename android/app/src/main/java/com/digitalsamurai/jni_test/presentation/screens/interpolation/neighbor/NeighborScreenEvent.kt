package com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class NeighborScreenEvent : UiEvent() {
    sealed class BitmapSaving : NeighborScreenEvent() {
        class Success(val fileName: String) : NeighborScreenEvent()
        data object Failed : NeighborScreenEvent()
    }
}