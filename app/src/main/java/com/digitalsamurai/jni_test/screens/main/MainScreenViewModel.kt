package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.interpolation.linear.LinearScreen
import com.digitalsamurai.jni_test.screens.interpolation.neighbor.NeighborScreen
import com.digitalsamurai.jni_test.view.ImageSelector
import com.digitalsamurai.jni_test.view.items.FeatureItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ScreenViewModel<MainScreenState, MainScreenEvent, MainScreenActions>(), MainScreenActions {

    override fun initialState(): MainScreenState = MainScreenState(
        imageSelectorState = ImageSelector.defaultState(),
        featuresItems = listOf(
            FeatureItem.State(
                id = "linear_interpolation",
                title = "Linear interpolation",
                icon = null
            ),
            FeatureItem.State(
                id = "neares_neighbor_interpolation",
                title = "Nearest neighbor interpolation",
                icon = null
            )
        )
    )


    override fun generateNoises() {

    }

    override fun onFeatureItemClicked(itemId: String) {
        when (itemId) {
            "linear_interpolation" -> {
                navigateTo(LinearScreen)
            }

            "neares_neighbor_interpolation" -> {
                navigateTo(NeighborScreen)
            }

            else -> {
                event(MainScreenEvent.UnknownFeature(itemId))
            }
        }
    }
}