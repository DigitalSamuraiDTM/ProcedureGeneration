package com.digitalsamurai.jni_test.presentation.screens.auth

import com.digitalsamurai.jni_test.core.viewmodel.UiState

data class AuthScreenState(
    val login: Login,
    val password: Password,
    val isLoginButtonLoading: Boolean,
    val isAnonymButtonEnabled: Boolean,
): UiState() {

    data class Login(
        val text: String,
        val isEnabled: Boolean,
        val isLoading: Boolean,
        val isError: Boolean,
    )

    data class Password(
        val text: String,
        val isEnabled: Boolean,
        val isError: Boolean,
    )

    companion object {
        fun default(): AuthScreenState = AuthScreenState(
            login = Login("", true, false, false),
            password = Password("", true, false),
            isLoginButtonLoading = false,
            isAnonymButtonEnabled = true,
        )
    }
}