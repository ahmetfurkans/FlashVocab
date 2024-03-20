package com.svmsoftware.flashvocab.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.svmsoftware.flashvocab.feature_bookmarks.presentation.BookmarksScreen
import com.svmsoftware.flashvocab.feature_home.presentation.HomeScreen
import com.svmsoftware.flashvocab.feature_setting.presentation.SettingsScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.name, modifier = modifier
    ) {
        composable(route = Screen.Home.name) {
            HomeScreen()
        }
        composable(route = Screen.Bookmarks.name) {
            BookmarksScreen()
        }
        composable(route = Screen.Settings.name) {
            SettingsScreen()
        }
    }
}