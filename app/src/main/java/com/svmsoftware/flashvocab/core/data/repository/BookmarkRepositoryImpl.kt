package com.svmsoftware.flashvocab.core.data.repository

import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.core.data.local.BookmarkDao
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {

    override suspend fun getBookmarkById(id: Int): Bookmark? {
        return dao.getBookmarkById(id)
    }

    override fun getBookmarks(query: String): Flow<List<Bookmark>> {
        return dao.getBookmarks2(query)
    }

    override suspend fun insertBookmark(bookmark: Bookmark): Resource<Bookmark> {
        return try {
            if (bookmark.originalText.isBlank()) {
                Resource.Error(desc = "The original text can't be empty.")
            } else if (bookmark.translatedText.isBlank()) {
                Resource.Error(desc = "Please complete the translation before adding a bookmark.")
            } else {
                dao.insertBookmark(bookmark)
                Resource.Success(data = null)
            }
        } catch (e: IllegalArgumentException) {
            Resource.Error("Unsupported Language")
        }
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark)
    }

    override suspend fun deleteAllBookmarks() {
        dao.deleteAllBookmark()
    }
}