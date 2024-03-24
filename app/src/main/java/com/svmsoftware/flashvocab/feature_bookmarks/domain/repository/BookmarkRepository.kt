package com.svmsoftware.flashvocab.feature_bookmarks.domain.repository

import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.feature_bookmarks.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getBookmarks(): Flow<List<Bookmark>>

    suspend fun getBookmarkById(id: Int): Bookmark?

    suspend fun insertBookmark(bookmark: Bookmark): Resource<Bookmark>

    suspend fun deleteBookmark(bookmark: Bookmark)

}