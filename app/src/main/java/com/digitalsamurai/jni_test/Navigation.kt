package com.digitalsamurai.jni_test

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.screens.linear.LinearScreen
import com.digitalsamurai.jni_test.screens.main.MainScreen
import com.digitalsamurai.jni_test.screens.neighbor.NeighborScreen
import com.digitalsamurai.jni_test.screens.settings.SettingsScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = startScreen.screenRoute) {
        appsScreen.forEach { screen ->
            composable(route = screen.screenRoute) {
                screen.NavToScreen(navController)
            }
        }
    }
}

val startScreen: BaseScreen<*, *, *> = MainScreen

val appsScreen = setOf<BaseScreen<*, *, *>>(SettingsScreen, MainScreen, LinearScreen, NeighborScreen)