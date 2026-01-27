package com.digitalsamurai.jni_test.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.core.views.MyButton
import com.digitalsamurai.jni_test.presentation.theme.AppTheme
import com.digitalsamurai.jni_test.presentation.theme.ThemeMod
import io.opentelemetry.api.trace.Span

internal object AuthScreen : BaseScreen<AuthScreenState, AuthScreenEvent, AuthScreenActions>() {

    override val screenName: String = "AuthScreen"
    override val routeName: String = "auth"
    override val isNavigationBarEnabled: Boolean = false

    override suspend fun onEvent(
        event: AuthScreenEvent,
        actions: AuthScreenActions,
        snackbar: SnackbarHostState
    ) {
        when (event) {
            AuthScreenEvent.AuthException -> snackbar.showSnackbar("Authorization failed")
        }
    }

    @Composable
    override fun MakeViewModel(
        screenSpan: Span,
        navigator: AppNavigator
    ): AuthScreenViewModel {
        return hiltViewModel<AuthScreenViewModel, AuthScreenViewModel.Factory> { f ->
            f.build(screenSpan, navigator)
        }
    }

    @Composable
    override fun Screen(
        state: AuthScreenState,
        actions: AuthScreenActions
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.wrapContentSize()
            ) {
                // login block
                TextField(
                    isError = state.login.isError,
                    enabled = state.login.isEnabled,
                    placeholder = { Text("Login") },
                    value = state.login.text,
                    onValueChange = actions::onLoginInput
                )
                TextField(
                    isError = state.password.isError,
                    enabled = state.password.isEnabled,
                    value = state.password.text,
                    placeholder = { Text("Password") },
                    onValueChange = actions::onPasswordInput,
                    visualTransformation = PasswordVisualTransformation()
                )

                // buttons block
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(0.6f)
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MyButton(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        buttonName = "AuthButton",
                        onClick = { if (!state.login.isLoading) actions.onLoginButtonClicked() }
                    ) {
                        if (state.login.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.White,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text("Login")
                        }
                    }
                    MyButton(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        buttonName = "AnonymButton",
                        onClick = actions::onAnonymButtonClicked
                    ) {
                        Text("Continue as anonym")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme(mod = ThemeMod.KOTLIN) {
        // TODO при использовании компонент со спанами не очень работает. Нужно научиться мокать спаны в превьюхе
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                AuthScreen.Screen(
                    state = AuthScreenState.default(),
                    actions = object : AuthScreenActions {
                        override fun onLoginButtonClicked() {}
                        override fun onAnonymButtonClicked() {}
                        override fun onLoginInput(text: String) {}
                        override fun onPasswordInput(text: String) {}
                    }
                )
            }
        }

    }
}
