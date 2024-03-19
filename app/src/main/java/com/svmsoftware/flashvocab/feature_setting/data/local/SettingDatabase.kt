package com.svmsoftware.flashvocab.feature_setting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings

@Database(
    entities = [UserSettings::class],
    version = 1
)
abstract class SettingDatabase: RoomDatabase() {

    abstract val dao: SettingDao

    companion object {
        const val DATABASE_NAME = "settings_db"
    }
}