package com.digitalsamurai.jni_test.screens.linear

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen

object LinearScreen : BaseScreen<LinearViewModel>() {

    override val routeName: String = "linear_interpolation"

    @Composable
    override fun MakeViewModel(): LinearViewModel {
        return hiltViewModel()
    }

    @Composable
    override fun Screen(viewModel: LinearViewModel) {

    }
}
