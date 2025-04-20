package com.digitalsamurai.jni_test.screens.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.algos.BitmapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryScreenViewModel @Inject constructor(
    private val bitmapRepository: BitmapRepository,
    @ApplicationContext private val applicationContext: Context
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
                        id = it.second
                    )
                }
                )
            }
        }
    }

    override fun onGalleryItemClicked(itemId: String?) {
        if(itemId != null) {
            val file = bitmapRepository.getFile(itemId)
            val bitmapUri = FileProvider.getUriForFile(
                applicationContext,
                "com.digitalsamurai.jni_test.fileprovider",
                file
            )
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, bitmapUri)
                type = "image/png"
            }
            applicationContext.startActivity(intent)
        } else {
            event(GalleryScreenEvent.Error)
        }
    }

    override fun onGalleryItemLoad(itemId: String): Deferred<Bitmap> {
        return viewModelScope.async {
            bitmapRepository.get(itemId)
        }
    }
}