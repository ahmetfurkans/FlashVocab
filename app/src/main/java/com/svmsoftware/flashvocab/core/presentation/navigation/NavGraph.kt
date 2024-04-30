package com.svmsoftware.flashvocab.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.svmsoftware.flashvocab.feature_bookmarks.BookmarksScreen
import com.svmsoftware.flashvocab.feature_home.HomeScreen
import com.svmsoftware.flashvocab.feature_setting.SettingsScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.name, modifier = modifier
    ) {
        composable(route = Screen.Home.name) {
            HomeScreen(snackbarHostState = snackbarHostState)
        }
        composable(route = Screen.Bookmarks.name) {
            BookmarksScreen()
        }
        composable(route = Screen.Settings.name) {
            SettingsScreen()
        }
    }
}