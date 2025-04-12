package com.digitalsamurai.jni_test.screens.storage

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface GalleryScreenActions : UiActions {

    fun onGalleryItemClicked(itemId: String?)
}