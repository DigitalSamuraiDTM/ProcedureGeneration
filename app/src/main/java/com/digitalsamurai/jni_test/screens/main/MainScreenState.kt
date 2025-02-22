package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.ImageSelector

public class MainScreenState(
    val imageSelectorState: ImageSelector.State
): UiState()