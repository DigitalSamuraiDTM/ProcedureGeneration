package com.digitalsamurai.jni_test.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.presentation.view.items.FeatureItem
import io.opentelemetry.api.trace.Span

object MainScreen : BaseScreen<MainScreenState, MainScreenEvent, MainScreenActions>() {

    override val routeName: String = "main"
    override val screenName: String = "MainScreen"
    override val isNavigationBarEnabled: Boolean = true

    @Composable
    override fun MakeViewModel(screenSpan: Span, navigator: AppNavigator): MainScreenViewModel {
        return hiltViewModel<MainScreenViewModel, MainScreenViewModel.Factory> { f ->
            f.get(screenSpan, navigator)
        }
    }

    override suspend fun onEvent(
        event: MainScreenEvent,
        actions: MainScreenActions,
        snackbar: SnackbarHostState
    ) {
        when (event) {
            is MainScreenEvent.UnknownFeature -> snackbar.showSnackbar(
                message = "Unknown feature!",
                actionLabel = "Obama",
                duration = SnackbarDuration.Short
            )
        }
    }

    @Composable
    override fun Screen(
        state: MainScreenState,
        actions: MainScreenActions,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.featuresItems) { feature ->
                    FeatureItem(
                        modifier = Modifier
                            .height(110.dp)
                            .fillMaxWidth(),
                        state = feature,
                        onClick = { actions.onFeatureItemClicked(feature.id) }
                    )
                }
            }
        }
    }
}