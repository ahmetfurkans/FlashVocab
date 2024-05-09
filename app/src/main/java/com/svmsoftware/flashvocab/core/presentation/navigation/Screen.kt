package com.svmsoftware.flashvocab.core.presentation.navigation

import androidx.annotation.StringRes
import com.svmsoftware.flashvocab.R

enum class Screen(@StringRes val title: Int) {
    Home(title = R.string.home),
    Bookmarks(title = R.string.bookmarks),
    Settings(title = R.string.settings),
}