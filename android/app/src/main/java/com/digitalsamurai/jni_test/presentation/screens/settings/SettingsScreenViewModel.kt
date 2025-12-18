package com.digitalsamurai.jni_test.presentation.screens.settings

import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.presentation.screens.main.MainScreen
import com.digitalsamurai.jni_test.presentation.theme.ThemeController
import com.digitalsamurai.jni_test.presentation.theme.ThemeMod
import com.digitalsamurai.jni_test.presentation.view.ModConverter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.opentelemetry.api.trace.Span

@HiltViewModel(assistedFactory = SettingsScreenViewModel.Factory::class)
class SettingsScreenViewModel @AssistedInject constructor(
    private val themeController: ThemeController,
    @Assisted private val navController: NavController,
    @Assisted private val screenSpan: Span,
) : ScreenViewModel<SettingsScreenState, SettingsScreenEvent, SettingsScreenActions>(
    screenSpan = screenSpan
), SettingsScreenActions {

    @AssistedFactory
    interface Factory {
        fun get(navController: NavController, screenSpan: Span): SettingsScreenViewModel
    }

    override fun initialState(): SettingsScreenState = SettingsScreenState(
        modConverter = ModConverter.defaultState()
    )

    fun toMainScreen() {
        navController.navigate(MainScreen.screenRoute)
    }

    override fun onConverterModSelected(mod: ModConverter.State.Mod) {
        updateState { state ->
            themeController.setThemeMod(mod = mod.toThemeMod())
            state.copy(modConverter = state.modConverter.copy(selectedMod = mod))
        }
    }

    private fun ModConverter.State.Mod.toThemeMod(): ThemeMod {
        return when (this) {
            ModConverter.State.Mod.NDK -> ThemeMod.NDK
            ModConverter.State.Mod.KOTLIN -> ThemeMod.KOTLIN
        }
    }
}