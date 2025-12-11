package com.digitalsamurai.jni_test.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.ModConverter
import io.opentelemetry.api.trace.Span


object SettingsScreen :
    BaseScreen<SettingsScreenState, SettingsScreenEvent, SettingsScreenActions>() {

    override val routeName: String = "settings"
    override val screenName: String = "SettingsScreen"


    @Composable
    override fun MakeViewModel(screenSpan: Span, navController: NavController): SettingsScreenViewModel {
        return hiltViewModel<SettingsScreenViewModel, SettingsScreenViewModel.Factory> { f ->
            f.get(navController, screenSpan)
        }
    }

    override suspend fun onEvent(
        event: SettingsScreenEvent,
        actions: SettingsScreenActions,
        snackbar: SnackbarHostState
    ) {

    }

    @Composable
    override fun Screen(
        state: SettingsScreenState,
        actions: SettingsScreenActions,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            ModConverter(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                state = state.modConverter
            ) { mod ->
                actions.onConverterModSelected(mod)
            }
        }
    }
}