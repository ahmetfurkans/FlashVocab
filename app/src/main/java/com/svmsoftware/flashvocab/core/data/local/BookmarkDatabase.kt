package com.svmsoftware.flashvocab.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svmsoftware.flashvocab.core.domain.model.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 3
)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract val dao: BookmarkDao

    companion object {
        const val DATABASE_NAME = "bookmarks_db"
    }
}