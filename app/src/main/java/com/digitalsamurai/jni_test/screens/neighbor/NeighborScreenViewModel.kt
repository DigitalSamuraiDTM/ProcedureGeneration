package com.digitalsamurai.jni_test.screens.neighbor

import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeighborScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) : ScreenViewModel<NeighborScreenState, NeighborScreenEvent, NeighborScreenActions>(), NeighborScreenActions {


    override fun initialState(): NeighborScreenState {
        return NeighborScreenState(
            bitmapRendererState = BitmapRenderer.State.Empty
        )
    }

    override fun onBitmapRendererClicked() {
        TODO("Not yet implemented")
    }

    override fun onGenerateButtonClicked() {
        viewModelScope.launch {
            val bitmap = bitmapGenerator.neighborBitmap(
                size = BitmapGenerator.Size(1000, 1000),
                neighborConfig = BitmapGenerator.NeighborConfig.Random(20)
            )
            updateState {
                it.copy(
                    bitmapRendererState = BitmapRenderer.State.Content(
                        bitmap = bitmap,
                        id = "id_example"
                    )
                )
            }
        }
    }
}