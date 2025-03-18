package com.digitalsamurai.jni_test.screens.linear

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel

class LinearViewModel : ScreenViewModel<LinearScreenState, LinearScreenEvent>() {

    override fun initialState(): LinearScreenState {
        return LinearScreenState("")
    }

}