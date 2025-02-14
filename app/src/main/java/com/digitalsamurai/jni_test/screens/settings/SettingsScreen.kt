package com.digitalsamurai.jni_test.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.ModConverter


object SettingsScreen : BaseScreen<SettingsScreenViewModel>() {

    override val routeName: String = "settings"

    @Composable
    override fun MakeViewModel(): SettingsScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(viewModel: SettingsScreenViewModel) {
        val screenState = viewModel.state.collectAsState().value
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)) {
            ModConverter(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
                state = screenState.modConverter) { mod ->
                viewModel.onConverterModSelected(mod)
            }
        }
    }
}