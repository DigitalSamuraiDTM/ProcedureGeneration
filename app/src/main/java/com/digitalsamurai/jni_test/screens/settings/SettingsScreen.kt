package com.digitalsamurai.jni_test.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen


object SettingsScreen: BaseScreen<SettingsScreenViewModel>() {

    override val routeName: String = "settings"

    @Composable
    override fun MakeViewModel(): SettingsScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(viewModel: SettingsScreenViewModel) {
        Box(modifier = Modifier.background(color = Color.White).fillMaxSize()) {
            Text("SETTINGS")
            Button(onClick = {
                viewModel.toMainScreen()
            }, modifier = Modifier.align(Alignment.Center)) {
                Text("TO MAIN")
            }
        }
    }
}