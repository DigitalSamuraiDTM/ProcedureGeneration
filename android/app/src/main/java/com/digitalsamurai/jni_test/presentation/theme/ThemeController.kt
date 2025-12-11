package com.digitalsamurai.jni_test.presentation.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeController @Inject constructor() {

    fun setThemeMod(mod: ThemeMod) {
        _currentMode.tryEmit(mod)
    }

    // TODO MIGRATE TO PREFERENCES
    private val _currentMode = MutableStateFlow<ThemeMod>(ThemeMod.KOTLIN)
    val currentMode: StateFlow<ThemeMod> = _currentMode
}