package com.digitalsamurai.jni_test.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.items.FeatureItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

object MainScreen : BaseScreen<MainScreenState, MainScreenEvent, MainScreenActions>() {

    override val routeName: String = "main"

    @Composable
    override fun MakeViewModel(): MainScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(
        state: MainScreenState,
        events: SharedFlow<MainScreenEvent>,
        actions: MainScreenActions,
    ) {
        val eventsScope = rememberCoroutineScope()
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            eventsScope.launch {
                // TODO: мигрировать на снекбары и вынести эвенты не в экран все таки, наверное. Предполагаю, что эвенты это события НЕ влияющие на состояние экрана
                events.collect { event ->
                    when (event) {
                        is MainScreenEvent.UnknownFeature -> Toast.makeText(
                            context,
                            "Unknown feature: ${event.name}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
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