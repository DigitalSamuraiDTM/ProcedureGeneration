package com.digitalsamurai.jni_test.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.ModConverter
import kotlinx.coroutines.flow.SharedFlow


object SettingsScreen : BaseScreen<SettingsScreenState, SettingsScreenEvent, SettingsScreenActions>() {

    override val routeName: String = "settings"

    @Composable
    override fun MakeViewModel(): SettingsScreenViewModel {
        return hiltViewModel()
    }

    @Composable
    override fun Screen(
        state: SettingsScreenState,
        events: SharedFlow<SettingsScreenEvent>,
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