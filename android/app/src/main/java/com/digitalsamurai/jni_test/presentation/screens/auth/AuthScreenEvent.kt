package com.digitalsamurai.jni_test.presentation.screens.auth

import com.digitalsamurai.jni_test.core.viewmodel.UiEvent

sealed class AuthScreenEvent: UiEvent() {
    data object AuthException: AuthScreenEvent()
}