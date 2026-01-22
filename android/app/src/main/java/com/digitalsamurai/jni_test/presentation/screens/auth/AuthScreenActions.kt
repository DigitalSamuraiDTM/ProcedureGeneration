package com.digitalsamurai.jni_test.presentation.screens.auth

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface AuthScreenActions: UiActions {

    fun onLoginButtonClicked()

    fun onAnonymButtonClicked()

    fun onLoginInput(text: String)

    fun onPasswordInput(text: String)
}