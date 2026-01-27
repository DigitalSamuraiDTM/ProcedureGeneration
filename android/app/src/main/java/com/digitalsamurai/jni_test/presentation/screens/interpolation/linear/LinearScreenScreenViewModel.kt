package com.digitalsamurai.jni_test.presentation.screens.interpolation.linear

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.core.otel.extensions.endWithException
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.data.network.requests.frontback.GetLinearConfigRequest
import com.digitalsamurai.jni_test.domain.GenerateBilinearImageUseCase
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpClient
import com.digitsamurai.algos.BitmapGenerator
import com.digitalsamurai.jni_test.data.repositories.BitmapRepository
import com.digitsamurai.utils.extensions.generateName
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@HiltViewModel(assistedFactory = LinearScreenScreenViewModel.Factory::class)
class LinearScreenScreenViewModel @AssistedInject constructor(
    private val generateBilinearImageUseCase: GenerateBilinearImageUseCase,
    private val bitmapRepository: BitmapRepository,
    private val networkHttpClient: NetworkHttpClient,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearScreenActions>(
    screenSpan = screenSpan,
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
                BitmapRenderer.default(),
            isButtonLoading = false,
        )
    }

    override fun onBitmapRendererClicked(id: String) {
        // TODO
    }

    override fun undoImageSaving(id: String) {
        viewModelScope.launchTracedSafe("UndoImageSaving") {
            val isDeleted = bitmapRepository.delete(id)
            Log.d("OBAMA", "Image delete ${isDeleted}: ${id}")
        }
    }

    override fun onGenerateButtonClicked() {
        generatorJob?.cancel()
        generatorJob = viewModelScope.launchTracedSafe("GenerateLinearBitmap", Dispatchers.Default) {
            updateState {
                it.copy(isButtonLoading = true)
            }
            val configuration = networkHttpClient.makeNetworkRequest(
                networkHttpRequest = GetLinearConfigRequest(),
                data = Unit,
            ).getOrElse { t ->
                event(LinearScreenEvent.NetworkException)
                endWithException(t)
                updateState {
                    it.copy(isButtonLoading = false)
                }
                return@launchTracedSafe
            }

            val bitmap = generateBilinearImageUseCase(
                size = BitmapGenerator.Size(configuration.width, configuration.height),
                bilinearConfig = BitmapGenerator.BilinearConfig.Matrix(configuration.resolution)
            )
            val bitmapName = bitmap.generateName()
            updateState {
                it.copy(
                    bitmapRendererState = BitmapRenderer.State.ContentBitmap(
                        bitmap = bitmap,
                        id = bitmapName,
                    ),
                    isButtonLoading = false
                )
            }
            if (isAutosaveEnabled) {
                val isSaved = autosaveBitmap(bitmap, bitmapName)
                addEvent("AutoSaveBitmap", mapOf("is_saved" to isSaved.toString()))
            }
        }
    }

    private suspend fun autosaveBitmap(bitmap: Bitmap, name: String): Boolean {
        val isSaved = bitmapRepository.set(bitmap, BitmapRepository.Name.Value(name))
        val event = if (isSaved) LinearScreenEvent.BitmapSaving.Success(name) else LinearScreenEvent.BitmapSaving.Failed
        event(event)
        return isSaved
    }

}