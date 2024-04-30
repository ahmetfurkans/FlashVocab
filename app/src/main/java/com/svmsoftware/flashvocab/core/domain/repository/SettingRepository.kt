package com.svmsoftware.flashvocab.core.domain.repository

import com.svmsoftware.flashvocab.core.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getSettings(): Flow<UserSettings>

    suspend fun insertSetting(settings: UserSettings)

}