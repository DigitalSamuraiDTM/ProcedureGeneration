package com.digitalsamurai.jni_test.presentation.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.items.FeatureItem

data class MainScreenState(
    val featuresItems: List<FeatureItem.State>,
) : UiState()