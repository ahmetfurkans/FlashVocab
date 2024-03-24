package com.svmsoftware.flashvocab.feature_bookmarks.data.repository

import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.feature_bookmarks.data.local.BookmarkDao
import com.svmsoftware.flashvocab.feature_bookmarks.domain.model.Bookmark
import com.svmsoftware.flashvocab.feature_bookmarks.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override fun getBookmarks(): Flow<List<Bookmark>> {
        return dao.getBookmarks()
    }

    override suspend fun getBookmarkById(id: Int): Bookmark? {
        return dao.getBookmarkById(id)
    }

    override suspend fun insertBookmark(bookmark: Bookmark): Resource<Bookmark> {
        return if (bookmark.sourceText.isBlank()) {
            Resource.Error(desc = "The source text can't be empty.")
        } else {
            dao.insertBookmark(bookmark)
            Resource.Success(data = null)
        }
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark)
    }
}