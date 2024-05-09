package com.svmsoftware.flashvocab.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): Flow<List<Bookmark>>

    @Query(
        """
            SELECT * 
            FROM bookmark
            WHERE LOWER(originalText) LIKE '%' || LOWER(:query) || '%' OR
                LOWER(translatedText) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun getBookmarks2(query: String?): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE id = :id")
    suspend fun getBookmarkById(id: Int): Bookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmark")
    suspend fun deleteAllBookmark()

}