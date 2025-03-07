package com.digitalsamurai.jni_test.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen


object MainScreen : BaseScreen<MainScreenViewModel>() {

    override val routeName: String = "main"

    @Composable
    override fun MakeViewModel(): MainScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(viewModel: MainScreenViewModel) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {viewModel.generateNoise()}) { }
        }
    }
}