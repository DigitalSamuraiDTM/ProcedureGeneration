package com.digitalsamurai.jni_test.screens.interpolation.bicubic

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface BicubicScreenActions : UiActions {
    fun onGenerateButtonClicked()
    fun onBitmapRendererClicked(id: String)
}