package com.digitalsamurai.jni_test.screens.gallery

import android.graphics.Bitmap
import com.digitalsamurai.jni_test.core.viewmodel.UiActions
import kotlinx.coroutines.Deferred

interface GalleryScreenActions : UiActions {

    fun onGalleryItemClicked(itemId: String?)

    fun onGalleryItemLoad(itemId: String): Deferred<Bitmap>
}