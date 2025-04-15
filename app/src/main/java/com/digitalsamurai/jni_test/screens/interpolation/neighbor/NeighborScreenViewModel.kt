package com.digitalsamurai.jni_test.screens.interpolation.neighbor

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import com.digitsamurai.algos.BitmapRepository
import com.digitsamurai.utils.extensions.generateName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeighborScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
    private val bitmapRepository: BitmapRepository,
) : ScreenViewModel<NeighborScreenState, NeighborScreenEvent, NeighborScreenActions>(), NeighborScreenActions {

    private val isAutosaveEnabled: Boolean = true

    override fun initialState(): NeighborScreenState {
        return NeighborScreenState(
            bitmapRendererState = BitmapRenderer.State.Empty
        )
    }

    override fun undoImageSaving(bitmapId: String) {
        viewModelScope.launch {
            val isDeleted = bitmapRepository.delete(bitmapId)
            Log.d("OBAMA", "Image delete ${isDeleted}: ${bitmapId}")
        }
    }

    override fun onBitmapRendererClicked(id: String) {
        TODO("Not yet implemented")
    }

    override fun onGenerateButtonClicked() {
        viewModelScope.launch {
            val bitmap = bitmapGenerator.neighborBitmap(
                size = BitmapGenerator.Size(1000, 1000),
                neighborConfig = BitmapGenerator.NeighborConfig.Random(20)
            )
            val bitmapId = bitmap.generateName()
            updateState {
                it.copy(
                    bitmapRendererState = BitmapRenderer.State.ContentBitmap(
                        bitmap = bitmap,
                        id = bitmapId
                    )
                )
            }
            if (isAutosaveEnabled) autosaveBitmap(bitmap, bitmapId)
        }
    }

    private fun autosaveBitmap(bitmap: Bitmap, id: String) {
        val isSaved = bitmapRepository.set(bitmap, BitmapRepository.Name.Value(id))
        val event = if (isSaved) {
            NeighborScreenEvent.BitmapSaving.Success(id)
        } else {
            NeighborScreenEvent.BitmapSaving.Failed
        }
        event(event)
    }
}