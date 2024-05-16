package com.svmsoftware.flashvocab.core.data.repository

import android.content.Context
import androidx.room.Room
import com.svmsoftware.flashvocab.core.data.local.DailyResetDao
import com.svmsoftware.flashvocab.core.data.local.DailyResetDatabase
import com.svmsoftware.flashvocab.core.data.local.SettingDao
import com.svmsoftware.flashvocab.core.domain.model.DailyReset
import com.svmsoftware.flashvocab.core.domain.repository.DailyResetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyResetRepositoryImpl @Inject constructor(
    private val database: DailyResetDatabase,
    @ApplicationContext private val context: Context,

    ) : DailyResetRepository {

    override fun getDailyReset(): Flow<DailyReset> {
        return database.dao.getDailyResetData()
    }

    override fun resetDatabase() {
        database.close()
        context.deleteDatabase(DailyResetDatabase.DATABASE_NAME)
        Room.databaseBuilder(
            context, DailyResetDatabase::class.java, DailyResetDatabase.DATABASE_NAME
        ).build()
    }

    override suspend fun insertDailyReset(dailyReset: DailyReset) {
        database.dao.updateDailyReset(dailyReset)
    }
}