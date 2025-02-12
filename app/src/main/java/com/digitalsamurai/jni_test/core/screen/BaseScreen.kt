package com.digitalsamurai.jni_test.core.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.viewmodel.ScreenViewModel
// TODO короче надо сделать так, чтобы в SCREEN передавалась не вьюмодель целиковая, а только STATE, SIDE EFFECT, и ACTIONS
// в ACTIONS надо описать список доступных действий для экрана, чтобы не прокидывать в Screen viewmodelьку и можно было смотреть compose preview
abstract class BaseScreen<VIEWMODEL : ScreenViewModel<*, *>> {

    protected abstract val routeName: String
    public val screenRoute get() = ROOT + routeName

    protected lateinit var viewModel: VIEWMODEL

    /**
     * Entry point for screen from navigation
     */
    @Composable
    public fun NavToScreen(navController: NavController) {
        viewModel = MakeViewModel()
        //TODO: INJECT VIA HILT OR DAGGER
        viewModel.setNavController(navController)
        Surface(modifier = Modifier.fillMaxSize()) {
            Screen(viewModel)
        }
    }

    @Composable
    protected abstract fun MakeViewModel(): VIEWMODEL

    @SuppressLint("NotConstructor")
    @Composable
    public abstract fun Screen(viewModel: VIEWMODEL)

    public companion object {
        const val ROOT = "root/"
    }
}