package com.digitalsamurai.jni_test.core.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.core.viewmodel.UiActions
import com.digitalsamurai.jni_test.core.viewmodel.UiEvent
import com.digitalsamurai.jni_test.core.viewmodel.UiState
import kotlinx.coroutines.flow.SharedFlow


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

        Surface(modifier = Modifier.fillMaxSize()) {
            Screen(state = state, events = events, actions = viewModel.getActions())
        }
    }

    @Composable
    protected abstract fun MakeViewModel(): ScreenViewModel<STATE, EVENTS, ACTIONS>

    @Composable
    abstract fun Screen(state: STATE, events: SharedFlow<EVENTS>, actions: ACTIONS)

    companion object {
        const val ROOT = "root/"
    }
}