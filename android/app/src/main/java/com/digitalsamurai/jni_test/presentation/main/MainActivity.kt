package com.digitalsamurai.jni_test.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.jni_test.core.navigation.Navigation
import com.digitalsamurai.jni_test.data.network.repository.AuthRepository
import com.digitalsamurai.jni_test.presentation.screens.auth.AuthScreen
import com.digitalsamurai.jni_test.presentation.screens.gallery.GalleryScreen
import com.digitalsamurai.jni_test.presentation.screens.main.MainScreen
import com.digitalsamurai.jni_test.presentation.screens.settings.SettingsScreen
import com.digitalsamurai.jni_test.presentation.theme.AppTheme
import com.digitalsamurai.jni_test.presentation.theme.ThemeController
import com.digitalsamurai.jni_test.presentation.view.bottombar.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeController: ThemeController

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val isAuthExist = authRepository.isTokenExist()
        val startScreen = if (isAuthExist) {
            MainScreen
        } else {
            AuthScreen
        }
        setContent {
            val navController = rememberNavController()
            val theme = themeController.currentMode.collectAsState()
            val isNavBarEnabled = remember { mutableStateOf(startScreen.isNavigationBarEnabled) }
            val navBarHeight = animateDpAsState(if (isNavBarEnabled.value) 60.dp else 0.dp)
            val scheme = AppTheme(mod = theme.value) {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(navBarHeight.value),
                        navController = navController,
                        items = bottomBarItems
                    )
                }) { paddings ->
                    Navigation(
                        modifier = Modifier.padding(paddings),
                        navController = navController,
                        startScreen = startScreen,
                        onNavigateToScreen = { screen ->
                            isNavBarEnabled.value = screen.isNavigationBarEnabled
                        }
                    )
                }
            }
            // выставляем ручками. Эта логика прокручивается только при реальной смене цвета и не обновляет цвет если не надо
            window.navigationBarColor = scheme.surfaceContainer.toArgb()
            // todo починить, чет не хочет работать
            window.statusBarColor = scheme.background.toArgb()
            val insetController = WindowCompat.getInsetsController(window, window.decorView)
            insetController.isAppearanceLightStatusBars = !isSystemInDarkTheme()
        }
    }

    val bottomBarItems = listOf(
        BottomBar.StateItem(
            route = MainScreen.screenRoute,
            title = "Generation",
            icon = Icons.Default.Favorite
        ),
        BottomBar.StateItem(
            route = SettingsScreen.screenRoute,
            title = "Settings",
            icon = Icons.Default.Settings
        ),
        BottomBar.StateItem(
            route = GalleryScreen.screenRoute,
            title = "Gallery",
            icon = Icons.AutoMirrored.Filled.List
        )
    )


    /**
     * A native method that is implemented by the 'jni_test' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String
//
//    external fun obama(input: String): String

    override fun onStop() {
        Log.d("OBAMA", "ON STOP")
        super.onStop()
    }

    override fun onStart() {
        Log.d("OBAMA", "ON START")
        super.onStart()
    }

    override fun onDestroy() {
        Log.d("OBAMA", "ON DESTROY")
        super.onDestroy()
    }

    companion object {
        // Used to load the 'jni_test' library on application startup.
        init {
            System.loadLibrary("jni_test")
        }
    }
}