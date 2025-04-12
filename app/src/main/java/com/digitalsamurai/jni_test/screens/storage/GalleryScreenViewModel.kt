package com.digitalsamurai.jni_test.screens.storage

import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryScreenViewModel @Inject constructor(
    private val bitmapRepository: BitmapRepository,
) : ScreenViewModel<GalleryScreenState, GalleryScreenEvent, GalleryScreenActions>(), GalleryScreenActions {

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
                        path = it.first,
                        id = it.second
                    )
                }
                )
            }
        }
    }

    override fun onGalleryItemClicked(itemId: String?) {

    }
}