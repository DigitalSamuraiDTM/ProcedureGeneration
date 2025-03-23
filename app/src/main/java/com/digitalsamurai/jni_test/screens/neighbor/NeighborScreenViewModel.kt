package com.digitalsamurai.jni_test.screens.neighbor

import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import kotlinx.coroutines.launch
import javax.inject.Inject

class NeighborScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) : ScreenViewModel<NeighborScreenState, NeighborScreenEvent, NeighborScreenActions>(), NeighborScreenActions {


    override fun initialState(): NeighborScreenState {
        return NeighborScreenState(
            bitmapRendererState = BitmapRenderer.State.Empty
        )
    }

    override fun onBitmapRendererClicked() {
        viewModelScope.launch {
            val bitmap = bitmapGenerator
        }
    }

    override fun onGenerateButtonClicked() {
        TODO("Not yet implemented")
    }
}