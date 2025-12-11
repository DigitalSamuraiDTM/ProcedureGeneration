package com.digitalsamurai.jni_test.presentation.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.UiActions

interface MainScreenActions : UiActions {
    fun generateNoises()
    fun onFeatureItemClicked(itemId: String)
}