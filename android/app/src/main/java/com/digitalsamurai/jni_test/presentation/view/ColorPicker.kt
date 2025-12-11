package com.digitalsamurai.jni_test.presentation.view

import androidx.compose.runtime.Composable

object ColorPicker {

    data class State(
        val toDo: String = "make color picker"
    )

    @Composable
    operator fun invoke(){

    }
}