package com.digitalsamurai.jni_test.screens.settings

import com.digitalsamurai.jni_test.core.viewmodel.UiActions
import com.digitalsamurai.jni_test.view.ModConverter

interface SettingsScreenActions : UiActions {
    fun onConverterModSelected(mod: ModConverter.State.Mod)
}