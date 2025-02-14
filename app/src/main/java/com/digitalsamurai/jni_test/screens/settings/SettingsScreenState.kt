package com.digitalsamurai.jni_test.screens.settings

import com.digitalsamurai.jni_test.core.viewmodel.UiState
import com.digitalsamurai.jni_test.view.ModConverter

public data class SettingsScreenState(
    public val modConverter: ModConverter.State
): UiState()