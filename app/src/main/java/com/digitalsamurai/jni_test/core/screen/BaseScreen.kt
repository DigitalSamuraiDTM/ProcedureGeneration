package com.digitalsamurai.jni_test.core.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.extensions.startScreenSpan
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.core.viewmodel.UiActions
import com.digitalsamurai.jni_test.core.viewmodel.UiEvent
import com.digitalsamurai.jni_test.core.viewmodel.UiState
import io.opentelemetry.api.trace.Span


abstract class BaseScreen<STATE : UiState, EVENTS : UiEvent, ACTIONS : UiActions> {

    /**
     * WITHOUT SLASH///// !
     */
    protected abstract val screenName: String
    protected abstract val routeName: String

    val screenRoute get() = ROOT + routeName
    private var screenSpan: Span? = null

    /**
     * Entry point for screen from navigation
     */
    @Composable
    fun NavToScreen(navController: NavController) {
        val viewModel = MakeViewModel()
        //TODO: INJECT VIA HILT OR DAGGER
        viewModel.setNavController(navController)

        val state = viewModel.state.collectAsState().value
        val events = viewModel.events
        val context = LocalContext.current

        val snackbarHostState = remember { SnackbarHostState() }
        val screenSpan = remember { context.startScreenSpan(screenName) }

        //TODO: интересно, что показ снекбара работает под мьютексом и может быть показан всегда один снекбар
        // если показать снекбар без таймаута с дисмисом по экшену, то это приведет к тому, что мы заблокируем чтение событий пока пользователь не примет действие
        // то есть одновременно мы можем показывать лишь одно действие. Надо подумать ок ли это)

        // TODO: дизайн снекбаров поправить, а то кринжово выглядят

        // events flow collecting
        LaunchedEffect(Unit) {
            events.collect { event ->
                onEvent(event, viewModel.getActions(), snackbarHostState)
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                screenSpan.end()
            }
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(snackbarHostState, modifier = Modifier.fillMaxSize())
            Screen(state = state, actions = viewModel.getActions())
        }
    }

    protected abstract suspend fun onEvent(
        event: EVENTS,
        actions: ACTIONS,
        snackbar: SnackbarHostState
    )

    @Composable
    protected abstract fun MakeViewModel(): ScreenViewModel<STATE, EVENTS, ACTIONS>

    @Composable
    abstract fun Screen(state: STATE, actions: ACTIONS)

    companion object {
        const val ROOT = "root/"
    }
}