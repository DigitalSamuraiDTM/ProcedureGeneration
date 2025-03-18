package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen
import com.digitalsamurai.jni_test.view.ImageSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ScreenViewModel<MainScreenState, MainScreenEvent, MainScreenActions>(), MainScreenActions {

    override fun initialState(): MainScreenState = MainScreenState(
        imageSelectorState = ImageSelector.defaultState()
    )

    fun toSettingsScreen() {
        navigateTo(SettingsScreen)
    }

    override fun generateNoises() {

    }
}