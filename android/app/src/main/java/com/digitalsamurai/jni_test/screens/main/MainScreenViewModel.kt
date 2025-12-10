package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.interpolation.bicubic.BicubicScreen
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
            LinearScreen.screenRoute -> navigateTo(LinearScreen)


            NeighborScreen.screenRoute -> navigateTo(NeighborScreen)


            BicubicScreen.screenRoute -> navigateTo(BicubicScreen)

            else -> {
                event(MainScreenEvent.UnknownFeature(itemId))
            }
        }
    }
}