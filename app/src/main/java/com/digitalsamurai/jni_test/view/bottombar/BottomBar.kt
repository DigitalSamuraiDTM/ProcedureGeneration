package com.digitalsamurai.jni_test.view.bottombar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

object BottomBar {

    data class StateItem(
        val route: String,
        val title: String,
        val icon: ImageVector,
    )

    @Composable
    operator fun invoke(modifier: Modifier = Modifier, items: List<StateItem>, navController: NavHostController) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomAppBar(modifier = modifier) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = { Icon(item.icon, item.title) },
                    label = { Text(item.title) })
            }
        }
    }
}