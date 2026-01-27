package com.digitalsamurai.jni_test.presentation.screens.auth

import androidx.lifecycle.viewModelScope
import com.digitalsamurai.core.otel.extensions.setException
import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.data.network.repository.AuthRepository
import com.digitalsamurai.jni_test.data.network.requests.auth.PostAuthRequest
import com.digitalsamurai.jni_test.presentation.screens.main.MainScreen
import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpClient
import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

@HiltViewModel(assistedFactory = AuthScreenViewModel.Factory::class)
internal class AuthScreenViewModel @AssistedInject constructor(
    @Assisted private val span: Span,
    @Assisted private val navigator: AppNavigator,
    private val networkHttpClient: NetworkHttpClient,
    private val authRepository: AuthRepository,
) : ScreenViewModel<AuthScreenState, AuthScreenEvent, AuthScreenActions>(span), AuthScreenActions {
    override fun initialState(): AuthScreenState = AuthScreenState.default()

    @AssistedFactory
    interface Factory {
        fun build(screenSpan: Span, navigator: AppNavigator): AuthScreenViewModel
    }

    private var authJob: Job? = null
    override fun onLoginButtonClicked() {
        if (authJob?.isActive != true) {
            authJob = viewModelScope.launchTracedSafe("Authorization", Dispatchers.IO) {
                updateState {
                    it.copy(
                        login = it.login.copy(isEnabled = false, isLoading = true),
                        password = it.password.copy(isEnabled = false),
                        isAnonymButtonEnabled = false,
                    )
                }
                val result = networkHttpClient.makeNetworkRequest(
                    networkHttpRequest = PostAuthRequest(),
                    data = PostAuthRequest.RequestData(
                        login = state.value.login.text,
                        password = state.value.password.text
                    )
                ).getOrElse { t ->
                    setException(t)
                    event(AuthScreenEvent.AuthException)
                    updateState {
                        it.copy(
                            login = it.login.copy(isError = true, isEnabled = true, isLoading = false),
                            password = it.password.copy(isError = true, isEnabled = true)
                        )
                    }
                    return@launchTracedSafe
                }
                authRepository.set(Jwt(result.token))
                withContext(Dispatchers.Main) {
                    navigator.newRoot(MainScreen)
                }
            }
        }
    }

    override fun onAnonymButtonClicked() {
        navigator.newRoot(MainScreen)
    }


    override fun onLoginInput(text: String) = updateState {
        it.copy(login = it.login.copy(text = text))
    }

    override fun onPasswordInput(text: String) = updateState {
        it.copy(password = it.password.copy(text = text))
    }
}