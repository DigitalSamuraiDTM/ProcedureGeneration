package com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.jni_test.core.navigation.AppNavigator
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.data.repositories.BitmapRepository
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import com.digitsamurai.utils.extensions.generateName
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span

@HiltViewModel(assistedFactory = NeighborScreenViewModel.Factory::class)
class NeighborScreenViewModel @AssistedInject constructor(
    private val bitmapGenerator: BitmapGenerator,
    private val bitmapRepository: BitmapRepository,
    @Assisted private val screenSpan: Span,
    @Assisted private val navigator: AppNavigator,
) : ScreenViewModel<NeighborScreenState, NeighborScreenEvent, NeighborScreenActions>(
    screenSpan = screenSpan,
), NeighborScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navigator: AppNavigator): NeighborScreenViewModel
    }

    private val isAutosaveEnabled: Boolean = true

    override fun initialState(): NeighborScreenState {
        return NeighborScreenState(
            bitmapRendererState = BitmapRenderer.State.Empty
        )
    }

    override fun undoImageSaving(bitmapId: String) {
        viewModelScope.launchTracedSafe("DeleteBitmap") {
            val isDeleted = bitmapRepository.delete(bitmapId)
            Log.d("OBAMA", "Image delete ${isDeleted}: ${bitmapId}")
        }
    }

    override fun onBitmapRendererClicked(id: String) {
        TODO("Not yet implemented")
    }

    override fun onGenerateButtonClicked() {
        viewModelScope.launchTracedSafe("GenerateNeighborBitmap") {
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
            if (isAutosaveEnabled) {
                val isSaved = autosaveBitmap(bitmap, bitmapId)
                addEvent("AutoSaveBitmap", mapOf("is_saved" to isSaved.toString()))
            }
        }
    }

    private suspend fun autosaveBitmap(bitmap: Bitmap, id: String): Boolean {
        val isSaved = bitmapRepository.set(bitmap, BitmapRepository.Name.Value(id))
        val event = if (isSaved) {
            NeighborScreenEvent.BitmapSaving.Success(id)
        } else {
            NeighborScreenEvent.BitmapSaving.Failed
        }
        event(event)
        return isSaved
    }
}