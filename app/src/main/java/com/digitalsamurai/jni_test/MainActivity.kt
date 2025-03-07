package com.digitalsamurai.jni_test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import com.digitalsamurai.jni_test.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeController: ThemeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val theme = themeController.currentMode.collectAsState()
            AppTheme(mod = theme.value) {
                Navigation()
            }
        }
    }


    /**
     * A native method that is implemented by the 'jni_test' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun obama(input: String): String

    companion object {
        // Used to load the 'jni_test' library on application startup.
        init {
            System.loadLibrary("jni_test")
        }
    }
}