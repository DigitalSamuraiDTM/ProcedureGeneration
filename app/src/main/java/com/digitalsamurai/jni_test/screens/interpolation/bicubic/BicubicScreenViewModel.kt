package com.digitalsamurai.jni_test.screens.interpolation.bicubic

import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BicubicScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) : ScreenViewModel<BicubicScreenState, BicubicScreenEvent, BicubicScreenActions>(), BicubicScreenActions {

    override fun initialState(): BicubicScreenState {
        return BicubicScreenState(BitmapRenderer.default())
    }

    override fun onGenerateButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
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