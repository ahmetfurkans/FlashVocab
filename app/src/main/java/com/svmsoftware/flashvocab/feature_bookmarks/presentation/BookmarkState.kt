package com.svmsoftware.flashvocab.feature_bookmarks.presentation

import com.svmsoftware.flashvocab.feature_bookmarks.domain.model.Bookmark

data class BookmarkState(
    val bookmarks: List<Bookmark> = emptyList(),
)