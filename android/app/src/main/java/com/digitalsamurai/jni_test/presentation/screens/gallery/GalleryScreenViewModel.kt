package com.digitalsamurai.jni_test.presentation.screens.gallery

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import com.digitalsamurai.jni_test.data.repositories.BitmapRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@HiltViewModel(assistedFactory = GalleryScreenViewModel.Factory::class)
class GalleryScreenViewModel @AssistedInject constructor(
    private val bitmapRepository: BitmapRepository,
    @Assisted private val screenSpan: Span,
    @Assisted private val navController: NavController,
) : ScreenViewModel<GalleryScreenState, GalleryScreenEvent, GalleryScreenActions>(
    screenSpan = screenSpan,
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
        viewModelScope.launchTraced("LoadBitmaps", Dispatchers.IO) {
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