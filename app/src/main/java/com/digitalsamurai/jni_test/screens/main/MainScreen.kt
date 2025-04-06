package com.digitalsamurai.jni_test.screens.main

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.items.FeatureItem

object MainScreen : BaseScreen<MainScreenState, MainScreenEvent, MainScreenActions>() {

    override val routeName: String = "main"

    @Composable
    override fun MakeViewModel(): MainScreenViewModel {
        return viewModel()
    }

    override suspend fun onEvent(event: MainScreenEvent, actions: MainScreenActions, snackbar: SnackbarHostState) {
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
        Log.d("OBAMA", "${LocalContext.current.getDir("bitmaps", Context.MODE_PRIVATE).list().joinToString(", ")}")
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.featuresItems) { feature ->
                    FeatureItem(
                        modifier = Modifier.height(110.dp),
                        state = feature,
                        onClick = { actions.onFeatureItemClicked(feature.id) }
                    )
                }
            }
        }
    }
}