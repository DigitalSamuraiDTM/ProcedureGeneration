package com.digitalsamurai.jni_test.presentation.screens.main

import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.data.network.repository.AuthRepository
import com.digitalsamurai.jni_test.presentation.screens.interpolation.bicubic.BicubicScreen
import com.digitalsamurai.jni_test.presentation.screens.interpolation.linear.LinearScreen
import com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor.NeighborScreen
import com.digitalsamurai.jni_test.presentation.view.items.FeatureItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span

@HiltViewModel(assistedFactory = MainScreenViewModel.Factory::class)
class MainScreenViewModel @AssistedInject constructor(
    @Assisted private val screenSpan: Span,
    @Assisted private val navigator: AppNavigator,
    private val authRepository: AuthRepository,
) : ScreenViewModel<MainScreenState, MainScreenEvent, MainScreenActions>(
    screenSpan = screenSpan
), MainScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navigator: AppNavigator): MainScreenViewModel
    }

    override fun initialState(): MainScreenState {
        return MainScreenState(emptyList())
    }

    init {
        val features = buildList {
            add(
                FeatureItem.State(
                    id = LinearScreen.screenRoute,
                    title = "Linear interpolation",
                    icon = null
                )
            )
            if (authRepository.isTokenExist()) {
                add(
                    FeatureItem.State(
                        id = NeighborScreen.screenRoute,
                        title = "Nearest neighbor interpolation",
                        icon = null
                    ),
                )
                add(
                    FeatureItem.State(
                        id = BicubicScreen.screenRoute,
                        title = "Bicubic interpolation",
                        icon = null
                    )
                )
            }
        }
        updateState {
            it.copy(featuresItems = features)
        }
    }


    override fun generateNoises() {

    }

    override fun onFeatureItemClicked(itemId: String) {
        when (itemId) {
            LinearScreen.screenRoute -> navigator.navigate(LinearScreen)


            NeighborScreen.screenRoute -> navigator.navigate(NeighborScreen)


            BicubicScreen.screenRoute -> navigator.navigate(BicubicScreen)

            else -> {
                event(MainScreenEvent.UnknownFeature(itemId))
            }
        }
    }
}