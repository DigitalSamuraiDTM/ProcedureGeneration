package com.digitalsamurai.jni_test.screens.main

import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.interpolation.bicubic.BicubicScreen
import com.digitalsamurai.jni_test.screens.interpolation.linear.LinearScreen
import com.digitalsamurai.jni_test.screens.interpolation.neighbor.NeighborScreen
import com.digitalsamurai.jni_test.view.ImageSelector
import com.digitalsamurai.jni_test.view.items.FeatureItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span

@HiltViewModel(assistedFactory = MainScreenViewModel.Factory::class)
class MainScreenViewModel @AssistedInject constructor(
    private val otel: Otel,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<MainScreenState, MainScreenEvent, MainScreenActions>(
    otel = otel,
    screenSpan = screenSpan
), MainScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navController: NavController): MainScreenViewModel
    }

    override fun initialState(): MainScreenState = MainScreenState(
        imageSelectorState = ImageSelector.defaultState(),
        featuresItems = listOf(
            FeatureItem.State(
                id = LinearScreen.screenRoute,
                title = "Linear interpolation",
                icon = null
            ),
            FeatureItem.State(
                id = NeighborScreen.screenRoute,
                title = "Nearest neighbor interpolation",
                icon = null
            ),
            FeatureItem.State(
                id = BicubicScreen.screenRoute,
                title = "Bicubic interpolation",
                icon = null
            )
        )
    )


    override fun generateNoises() {

    }

    override fun onFeatureItemClicked(itemId: String) {
        when (itemId) {
            LinearScreen.screenRoute -> navController.navigate(LinearScreen.screenRoute)


            NeighborScreen.screenRoute -> navController.navigate(NeighborScreen.screenRoute)


            BicubicScreen.screenRoute -> navController.navigate(BicubicScreen.screenRoute)

            else -> {
                event(MainScreenEvent.UnknownFeature(itemId))
            }
        }
    }
}