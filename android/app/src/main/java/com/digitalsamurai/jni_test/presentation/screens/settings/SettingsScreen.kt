package com.digitalsamurai.jni_test.presentation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.presentation.view.ModConverter
import io.opentelemetry.api.trace.Span


object SettingsScreen :
    BaseScreen<SettingsScreenState, SettingsScreenEvent, SettingsScreenActions>() {

    override val routeName: String = "settings"
    override val screenName: String = "SettingsScreen"
    override val isNavigationBarEnabled: Boolean = true


    @Composable
    override fun MakeViewModel(screenSpan: Span, navigator: AppNavigator): SettingsScreenViewModel {
        return hiltViewModel<SettingsScreenViewModel, SettingsScreenViewModel.Factory> { f ->
            f.get(navigator, screenSpan)
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