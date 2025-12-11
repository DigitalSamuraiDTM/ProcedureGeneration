package com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface NeighborScreenActions : UiActions {
    fun onBitmapRendererClicked(id: String)
    fun onGenerateButtonClicked()
    fun undoImageSaving(bitmapId: String)
}