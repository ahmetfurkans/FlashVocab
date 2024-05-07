package com.svmsoftware.flashvocab.feature_bookmarks

import com.svmsoftware.flashvocab.core.domain.model.Bookmark

data class BookmarkState(
    val bookmarks: List<Bookmark> = emptyList(),
    val query: String = ""
)