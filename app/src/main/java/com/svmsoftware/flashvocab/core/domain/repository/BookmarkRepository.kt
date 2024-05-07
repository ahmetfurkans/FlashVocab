package com.svmsoftware.flashvocab.core.domain.repository

import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getBookmarks(): Flow<List<Bookmark>>

    suspend fun getBookmarkById(id: Int): Bookmark?

    fun getBookmarks2(query: String): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark): Resource<Bookmark>

    suspend fun deleteBookmark(bookmark: Bookmark)

}