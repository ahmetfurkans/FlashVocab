package com.svmsoftware.flashvocab.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.svmsoftware.flashvocab.feature.bookmarks.BookmarksScreen
import com.svmsoftware.flashvocab.feature.home.HomeScreen
import com.svmsoftware.flashvocab.feature.settings.SettingsScreen


@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Screen.Settings.name, modifier = modifier
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