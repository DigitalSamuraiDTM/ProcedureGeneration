package com.digitalsamurai.jni_test.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.ImageSelector


object MainScreen : BaseScreen<MainScreenViewModel>() {

    override val routeName: String = "main"

    @Composable
    override fun MakeViewModel(): MainScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(viewModel: MainScreenViewModel) {
        Box(modifier = Modifier.fillMaxSize()) {
            ImageSelector(
                state = ImageSelector.defaultState(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.25f)
                    .align(Alignment.Center),
                onImageSelectClicked = { viewModel.onImageSelect() },
                onImageDrop = { viewModel.onImageDrop() }
            )
        }
    }
}