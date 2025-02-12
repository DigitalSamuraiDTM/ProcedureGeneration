package com.digitalsamurai.jni_test.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.KotlinButton
import com.digitalsamurai.jni_test.view.NdkButton


object SettingsScreen : BaseScreen<SettingsScreenViewModel>() {

    override val routeName: String = "settings"

    @Composable
    override fun MakeViewModel(): SettingsScreenViewModel {
        return viewModel()
    }

    @Composable
    override fun Screen(viewModel: SettingsScreenViewModel) {
        Box(modifier = Modifier.fillMaxWidth().padding(all = 8.dp)) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.07f),
            ) {
                NdkButton(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)) { }
                Spacer(modifier = Modifier.width(8.dp))
                KotlinButton(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)) { }
            }
        }
    }
}