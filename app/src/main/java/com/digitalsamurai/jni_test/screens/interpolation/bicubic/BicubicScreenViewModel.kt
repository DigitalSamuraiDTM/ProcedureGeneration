package com.digitalsamurai.jni_test.screens.interpolation.bicubic

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BicubicScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) : ScreenViewModel<BicubicScreenState, BicubicScreenEvent, BicubicScreenActions>(), BicubicScreenActions {

    override fun initialState(): BicubicScreenState {
        return BicubicScreenState(BitmapRenderer.default())
    }

    override fun onGenerateButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onBitmapRendererClicked(id: String) {
        TODO("Not yet implemented")
    }
}