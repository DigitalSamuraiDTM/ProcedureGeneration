package com.digitalsamurai.jni_test.presentation.screens.interpolation.linear

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import com.digitsamurai.algos.BitmapRepository
import com.digitsamurai.utils.extensions.generateName
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@HiltViewModel(assistedFactory = LinearScreenScreenViewModel.Factory::class)
class LinearScreenScreenViewModel @AssistedInject constructor(
    private val bitmapGenerator: BitmapGenerator,
    private val bitmapRepository: BitmapRepository,
    @ApplicationContext
    private val applicationContext: Context,
    private val otel: Otel,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearScreenActions>(
    screenSpan = screenSpan,
    otel = otel
), LinearScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navController: NavController): LinearScreenScreenViewModel
    }

    private var generatorJob: Job? = null
    private val isAutosaveEnabled = true

    override fun initialState(): LinearScreenState {
        return LinearScreenState(
            bitmapRendererState =
                BitmapRenderer.default()
        )
    }

    override fun onBitmapRendererClicked(id: String) {
        TODO("Not yet implemented")
    }

    override fun undoImageSaving(id: String) {
        viewModelScope.launchTraced("UndoImageSaving") {
            val isDeleted = bitmapRepository.delete(id)
            Log.d("OBAMA", "Image delete ${isDeleted}: ${id}")
        }
    }

    override fun onGenerateButtonClicked() {
        generatorJob?.cancel()
        generatorJob = viewModelScope.launchTraced("GenerateLinearBitmap", Dispatchers.Default) {
            val bitmap = bitmapGenerator.bilinearBitmap(
                size = BitmapGenerator.Size(1000, 1000),
                bilinearConfig = BitmapGenerator.BilinearConfig.Matrix(3)
            )
            val bitmapName = bitmap.generateName()
            updateState {
                it.copy(
                    bitmapRendererState = BitmapRenderer.State.ContentBitmap(
                        bitmap = bitmap,
                        id = bitmapName
                    )
                )
            }
            if (isAutosaveEnabled) {
                val isSaved = autosaveBitmap(bitmap, bitmapName)
                addEvent("AutoSaveBitmap", mapOf("is_saved" to isSaved.toString()))
            }
        }
    }

    private fun autosaveBitmap(bitmap: Bitmap, name: String): Boolean {
        val isSaved = bitmapRepository.set(bitmap, BitmapRepository.Name.Value(name))
        val event = if (isSaved) LinearScreenEvent.BitmapSaving.Success(name) else LinearScreenEvent.BitmapSaving.Failed
        event(event)
        return isSaved
    }

}