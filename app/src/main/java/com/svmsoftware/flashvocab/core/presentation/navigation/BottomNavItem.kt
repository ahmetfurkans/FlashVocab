package com.svmsoftware.flashvocab.core.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String,
)

val BottomNavItems = listOf(
    BottomNavItem(
        label = Screen.Bookmarks.title, icon = Icons.Filled.Bookmark, route = Screen.Bookmarks.name
    ), BottomNavItem(
        label = Screen.Home.title, icon = Icons.Filled.Home, route = Screen.Home.name
    ), BottomNavItem(
        label = Screen.Settings.title, icon = Icons.Filled.Settings, route = Screen.Settings.name
    )
)
