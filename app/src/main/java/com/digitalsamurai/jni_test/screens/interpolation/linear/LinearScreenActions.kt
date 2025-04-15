package com.digitalsamurai.jni_test.screens.interpolation.linear

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface LinearScreenActions : UiActions {
    fun onBitmapRendererClicked(id: String)
    fun onGenerateButtonClicked()
    fun undoImageSaving(id: String)
}