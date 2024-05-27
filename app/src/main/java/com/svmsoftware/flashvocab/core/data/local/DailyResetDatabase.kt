package com.svmsoftware.flashvocab.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svmsoftware.flashvocab.core.domain.model.DailyReset


@Database(
    entities = [DailyReset::class],
    version = 1
)
abstract class DailyResetDatabase : RoomDatabase() {

    abstract val dao: DailyResetDao

    companion object {
        const val DATABASE_NAME = "daily_reset_db"
    }
}