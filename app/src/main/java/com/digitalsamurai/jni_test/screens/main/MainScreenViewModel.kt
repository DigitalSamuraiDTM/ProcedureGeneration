package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen
import com.digitalsamurai.jni_test.view.ImageSelector

class MainScreenViewModel: ScreenViewModel<MainScreenState, MainScreenEvent>() {

    override fun initialState(): MainScreenState = MainScreenState(
        imageSelectorState = ImageSelector.defaultState()
    )

    fun toSettingsScreen() {
        navigateTo(SettingsScreen)
    }

    fun onImageSelect() {
        event(MainScreenEvent.SelectImage)
    }

    fun onImageDrop() {

    }
}