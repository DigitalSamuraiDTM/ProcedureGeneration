package com.digitalsamurai.jni_test.screens.linear

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel

class LinearViewModel : ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearActions>(), LinearActions {

    override fun initialState(): LinearScreenState {
        return LinearScreenState("")
    }

}