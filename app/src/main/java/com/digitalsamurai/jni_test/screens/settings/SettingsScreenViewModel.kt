package com.digitalsamurai.jni_test.screens.settings

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.main.MainScreen
import com.digitalsamurai.jni_test.view.ModConverter

class SettingsScreenViewModel: ScreenViewModel<SettingsScreenState, SettingsScreenEvent>() {

    override fun initialState(): SettingsScreenState = SettingsScreenState(
        modConverter = ModConverter.defaultState()
    )

    fun toMainScreen() {
        navigateTo(MainScreen)
    }

    fun onConverterModSelected(mod: ModConverter.State.Mod) {
        updateState { state ->
            state.copy(modConverter = state.modConverter.copy(selectedMod = mod))
        }
    }
}