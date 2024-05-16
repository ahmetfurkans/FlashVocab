package com.svmsoftware.flashvocab.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.svmsoftware.flashvocab.core.domain.model.DailyReset
import com.svmsoftware.flashvocab.core.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyResetDao {

    @Query("SELECT * FROM dailyreset")
    fun getDailyResetData(): Flow<DailyReset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDailyReset(dailyReset: DailyReset)
}