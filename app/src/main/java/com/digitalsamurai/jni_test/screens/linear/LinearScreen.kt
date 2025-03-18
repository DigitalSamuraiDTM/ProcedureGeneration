package com.digitalsamurai.jni_test.screens.linear

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import kotlinx.coroutines.flow.SharedFlow

object LinearScreen : BaseScreen<LinearScreenState, LinearScreenEvent, LinearActions>() {

    override val routeName: String = "interpolation/linear"

    @Composable
    override fun MakeViewModel(): LinearViewModel {
        return hiltViewModel()
    }

    @Composable
    override fun Screen(
        state: LinearScreenState,
        event: SharedFlow<LinearScreenEvent>,
        actions: LinearActions,
    ) {

    }
}
