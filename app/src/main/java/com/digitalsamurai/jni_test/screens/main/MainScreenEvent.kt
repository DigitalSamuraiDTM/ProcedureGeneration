package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class MainScreenEvent : UiEvent() {

    data class UnknownFeature(val name: String) : MainScreenEvent()
}