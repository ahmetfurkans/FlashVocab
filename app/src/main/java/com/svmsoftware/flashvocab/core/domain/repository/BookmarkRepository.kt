package com.svmsoftware.flashvocab.core.domain.repository

import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarkById(id: Int): Bookmark?

    fun getBookmarks(query: String): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark): Resource<Bookmark>

    suspend fun deleteBookmark(bookmark: Bookmark)

    suspend fun deleteAllBookmarks()

}