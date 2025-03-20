package com.digitalsamurai.jni_test.screens.linear

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        events: SharedFlow<LinearScreenEvent>,
        actions: LinearActions,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // generation status
            // image
            // settings

        }
    }
}
