package com.digitalsamurai.jni_test.screens.settings

import com.digitalsamurai.jni_test.ThemeController
import com.digitalsamurai.jni_test.ThemeMod
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.main.MainScreen
import com.digitalsamurai.jni_test.view.ModConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val themeController: ThemeController,
): ScreenViewModel<SettingsScreenState, SettingsScreenEvent>() {

    override fun initialState(): SettingsScreenState = SettingsScreenState(
        modConverter = ModConverter.defaultState()
    )

    fun toMainScreen() {
        navigateTo(MainScreen)
    }

    fun onConverterModSelected(mod: ModConverter.State.Mod) {
        updateState { state ->
            themeController.setThemeMod(mod = mod.toThemeMod())
            state.copy(modConverter = state.modConverter.copy(selectedMod = mod))
        }
    }

    private fun ModConverter.State.Mod.toThemeMod(): ThemeMod {
        return when(this) {
            ModConverter.State.Mod.NDK -> ThemeMod.NDK
            ModConverter.State.Mod.KOTLIN -> ThemeMod.KOTLIN
        }
    }
}