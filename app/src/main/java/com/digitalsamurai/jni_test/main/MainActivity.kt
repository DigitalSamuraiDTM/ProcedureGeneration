package com.digitalsamurai.jni_test.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.jni_test.Navigation
import com.digitalsamurai.jni_test.screens.gallery.GalleryScreen
import com.digitalsamurai.jni_test.screens.main.MainScreen
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen
import com.digitalsamurai.jni_test.theme.AppTheme
import com.digitalsamurai.jni_test.theme.ThemeController
import com.digitalsamurai.jni_test.view.bottombar.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import io.opentelemetry.api.trace.Span
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeController: ThemeController

    private var activitySpan: Span? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            val theme = themeController.currentMode.collectAsState()
            val scheme = AppTheme(mod = theme.value) {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        navController = navController,
                        items = bottomBarItems
                    )
                }) { paddings ->
                    Navigation(
                        modifier = Modifier.padding(paddings),
                        navController = navController
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