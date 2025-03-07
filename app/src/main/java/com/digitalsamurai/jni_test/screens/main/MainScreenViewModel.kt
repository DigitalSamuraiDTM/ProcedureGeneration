package com.digitalsamurai.jni_test.screens.main

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen
import com.digitalsamurai.jni_test.view.ImageSelector
import com.digitalsamurai.noises.NoisesGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val noisesGenerator: NoisesGenerator
): ScreenViewModel<MainScreenState, MainScreenEvent>() {

    override fun initialState(): MainScreenState = MainScreenState(
        imageSelectorState = ImageSelector.defaultState()
    )

    fun toSettingsScreen() {
        navigateTo(SettingsScreen)
    }

    fun generateNoise() {

    }

    fun onImageDrop() {

    }
}