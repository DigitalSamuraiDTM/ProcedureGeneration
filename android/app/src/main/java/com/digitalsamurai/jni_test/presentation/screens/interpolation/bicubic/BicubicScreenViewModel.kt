package com.digitalsamurai.jni_test.presentation.screens.interpolation.bicubic

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Dispatchers

@HiltViewModel(assistedFactory = BicubicScreenViewModel.Factory::class)
class BicubicScreenViewModel @AssistedInject constructor(
    private val bitmapGenerator: BitmapGenerator,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<BicubicScreenState, BicubicScreenEvent, BicubicScreenActions>(
    screenSpan = screenSpan
), BicubicScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navController: NavController): BicubicScreenViewModel
    }

    override fun initialState(): BicubicScreenState {
        return BicubicScreenState(BitmapRenderer.default())
    }

    override fun onGenerateButtonClicked() {
        viewModelScope.launchTraced("GenerateBicubicBitmap", Dispatchers.Default) {

            val bitmap = bitmapGenerator.bicubicBitmap(
                size = BitmapGenerator.Size(1000, 1000)
            )

            updateState { state ->
                state.copy(
                    bitmapRendererState = BitmapRenderer.State.ContentBitmap(
                        bitmap, "example"
                    )
                )
            }
        }
    }

    override fun onBitmapRendererClicked(id: String) {
        TODO("Not yet implemented")
    }
}