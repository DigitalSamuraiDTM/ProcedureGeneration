package com.digitalsamurai.jni_test.screens.settings

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel

class SettingsScreenViewModel: ScreenViewModel<SettingsScreenState, SettingsScreenEvent>() {

    override fun initialState(): SettingsScreenState = SettingsScreenState()

    fun toMainScreen() {
        navigateTo(SettingsScreen)
    }
}