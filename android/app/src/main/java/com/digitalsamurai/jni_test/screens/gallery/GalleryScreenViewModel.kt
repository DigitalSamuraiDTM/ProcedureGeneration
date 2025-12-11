package com.digitalsamurai.jni_test.screens.gallery

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = GalleryScreenViewModel.Factory::class)
class GalleryScreenViewModel @AssistedInject constructor(
    private val bitmapRepository: BitmapRepository,
    private val otel: Otel,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<GalleryScreenState, GalleryScreenEvent, GalleryScreenActions>(
    screenSpan = screenSpan,
    otel = otel,
), GalleryScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(screenSpan: Span, navController: NavController): GalleryScreenViewModel
    }

    override fun initialState(): GalleryScreenState {
        return GalleryScreenState(bitmapItems = emptyList())
    }

    init {
        loadBitmaps()
    }

    private fun loadBitmaps() {
        viewModelScope.launch(Dispatchers.IO) {
            val paths = bitmapRepository.getMetaInfo()
            updateState { state ->
                state.copy(bitmapItems = paths.map {
                    BitmapRenderer.State.ContentBitmapPath(
                        id = it.second
                    )
                }
                )
            }
        }
    }

    override fun onGalleryItemClicked(itemId: String?) {

    }

    override fun onGalleryItemLoad(itemId: String): Deferred<Bitmap> {
        return viewModelScope.async {
            bitmapRepository.get(itemId)
        }
    }
}