package com.digitalsamurai.jni_test

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class ThemeController @Inject constructor() {

    public fun setThemeMod(mod: ThemeMod) {
        _currentMode.tryEmit(mod)
    }

    // TODO MIGRATE TO PREFERENCES
    private val _currentMode = MutableStateFlow<ThemeMod>(ThemeMod.NDK)
    public val currentMode: StateFlow<ThemeMod> = _currentMode
}