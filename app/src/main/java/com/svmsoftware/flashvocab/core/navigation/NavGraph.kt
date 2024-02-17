package com.svmsoftware.flashvocab.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.name
    ) {
        composable(route = Screen.Home.name) {

        }
        composable(route = Screen.Bookmarks.name) {

        }
        composable(route = Screen.Settings.name) {

        }
    }
}