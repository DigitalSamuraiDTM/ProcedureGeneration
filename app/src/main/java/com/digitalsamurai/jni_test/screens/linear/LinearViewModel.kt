package com.digitalsamurai.jni_test.screens.linear

import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinearViewModel @Inject constructor(

): ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearActions>(), LinearActions {

    override fun initialState(): LinearScreenState {
        return LinearScreenState("")
    }


}