package com.svmsoftware.flashvocab.feature_bookmarks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svmsoftware.flashvocab.feature_bookmarks.domain.model.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1
)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract val dao: BookmarkDao

    companion object {
        const val DATABASE_NAME = "bookmarks_db"
    }
}