package com.digitalsamurai.jni_test.screens.linear

import android.graphics.Color
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
import com.digitsamurai.algos.BitmapGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinearViewModel @Inject constructor(
    private val bitmapGenerator: BitmapGenerator,
) : ScreenViewModel<LinearScreenState, LinearScreenEvent, LinearActions>(), LinearActions {

    override fun initialState(): LinearScreenState {
        return LinearScreenState("")
    }

    init {

        viewModelScope.launch {
            val bitmap = bitmapGenerator.bilinearBitmap(
                size = BitmapGenerator.Size(1000, 1000),
                bilinearConfig = BitmapGenerator.BilinearConfig(
                    colorLB = Color.valueOf(0f, 1f, 1f),
                    colorRB = Color.valueOf(0f, 0f, 1f),
                    colorLT = Color.valueOf(1f, 0f, 0f),
                    colorRT = Color.valueOf(0f, 1f, 0f),
                )
            )
            val b = bitmap
        }
    }

}