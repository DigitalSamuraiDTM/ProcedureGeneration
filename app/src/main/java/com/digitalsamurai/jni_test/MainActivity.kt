package com.digitalsamurai.jni_test

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.digitalsamurai.jni_test.databinding.ActivityMainBinding
import com.digitalsamurai.jni_test.theme.AppTheme
import com.digitalsamurai.monochrome.JavaMonochromeConverter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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