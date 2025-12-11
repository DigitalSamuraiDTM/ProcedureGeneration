package com.digitalsamurai.jni_test.presentation.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.ImageSelector
import com.digitalsamurai.jni_test.presentation.view.items.FeatureItem

class MainScreenState(
    val imageSelectorState: ImageSelector.State,
    val featuresItems: List<FeatureItem.State>,
) : UiState()