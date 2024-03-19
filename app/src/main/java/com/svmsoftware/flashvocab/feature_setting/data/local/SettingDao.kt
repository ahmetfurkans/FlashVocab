package com.svmsoftware.flashvocab.feature_setting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {

    @Query("SELECT * FROM usersettings")
    fun getSettings(): Flow<UserSettings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(setting: UserSettings)

}