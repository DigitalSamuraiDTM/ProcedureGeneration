package com.digitalsamurai.jni_test.core.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel

abstract class BaseScreen<VIEWMODEL: ScreenViewModel<*,*>> {

    protected abstract val routeName: String
    public val screenRoute get() = ROOT+routeName

    /**
     * Entry point for screen from navigation
     */
    @Composable
    public fun NavToScreen(navController: NavController) {
        val viewModel = MakeViewModel()
        viewModel.setNavController(navController)
        Screen(viewModel)
    }

    @Composable
    protected abstract fun MakeViewModel(): VIEWMODEL

    @SuppressLint("NotConstructor")
    @Composable
    protected abstract fun Screen(viewModel: VIEWMODEL)

    public companion object {
        const val ROOT = "root/"
    }
}