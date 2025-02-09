package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen

class MainScreenViewModel: ScreenViewModel<MainScreenState, MainScreenEvent>() {

    override fun initialState(): MainScreenState = MainScreenState()

    fun toSettingsScreen() {
        navigateTo(SettingsScreen)
    }
}