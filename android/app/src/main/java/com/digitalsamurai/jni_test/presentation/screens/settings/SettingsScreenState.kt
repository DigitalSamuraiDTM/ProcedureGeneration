package com.digitalsamurai.jni_test.presentation.screens.settings

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.presentation.view.ModConverter

public data class SettingsScreenState(
    public val modConverter: ModConverter.State
) : UiState()