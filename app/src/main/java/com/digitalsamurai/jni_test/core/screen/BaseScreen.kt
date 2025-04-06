package com.digitalsamurai.jni_test.core.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.core.viewmodel.UiActions
import com.digitalsamurai.jni_test.core.viewmodel.UiEvent
import com.digitalsamurai.jni_test.core.viewmodel.UiState


abstract class BaseScreen<STATE : UiState, EVENTS : UiEvent, ACTIONS : UiActions> {

    protected abstract val routeName: String
    val screenRoute get() = ROOT + routeName

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

        val snackbarHostState = remember { SnackbarHostState() }

        // events flow collecting
        LaunchedEffect(Unit) {
            events.collect { event ->
                onEvent(event, snackbarHostState)
            }
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(snackbarHostState, modifier = Modifier.fillMaxSize())
            Screen(state = state, actions = viewModel.getActions())
        }
    }

    protected abstract suspend fun onEvent(event: EVENTS, snackbarHostState: SnackbarHostState)

    @Composable
    protected abstract fun MakeViewModel(): ScreenViewModel<STATE, EVENTS, ACTIONS>

    @Composable
    abstract fun Screen(state: STATE, actions: ACTIONS)

    companion object {
        const val ROOT = "root/"
    }
}