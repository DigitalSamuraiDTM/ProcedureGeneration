package com.digitalsamurai.jni_test.screens.linear

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapGenerator
import com.digitsamurai.algos.BitmapRepository
import com.digitsamurai.utils.extensions.generateName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinearScreenScreenViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
    private val bitmapRepository: BitmapRepository,
    @ApplicationContext
    private val applicationContext: Context,
) : ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearScreenActions>(), LinearScreenActions {

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
        viewModelScope.launch {
            val isDeleted = bitmapRepository.delete(id)
            Log.d("OBAMA", "Image delete ${isDeleted}: ${id}")
        }
    }

    override fun onGenerateButtonClicked() {
        generatorJob?.cancel()
        generatorJob = viewModelScope.launch {
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
            if (isAutosaveEnabled) autosaveBitmap(bitmap, bitmapName)
        }
    }

    private fun autosaveBitmap(bitmap: Bitmap, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = bitmapRepository.set(bitmap, BitmapRepository.Name.Value(name))
            val event = if (isSaved) LinearScreenEvent.BitmapSaving.Success(name) else LinearScreenEvent.BitmapSaving.Failed
            event(event)
        }
    }

}