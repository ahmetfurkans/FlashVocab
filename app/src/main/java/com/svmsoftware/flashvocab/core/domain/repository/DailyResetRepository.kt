package com.svmsoftware.flashvocab.core.domain.repository

import com.svmsoftware.flashvocab.core.domain.model.DailyReset
import kotlinx.coroutines.flow.Flow

interface DailyResetRepository {
    fun getDailyReset(): Flow<DailyReset>

    fun resetDatabase()
    suspend fun insertDailyReset(dailyReset: DailyReset)
}