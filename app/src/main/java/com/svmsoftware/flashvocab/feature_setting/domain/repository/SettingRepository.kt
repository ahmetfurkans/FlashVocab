package com.svmsoftware.flashvocab.feature_setting.domain.repository

import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getSettings(): Flow<UserSettings>

    suspend fun insertSetting(settings: UserSettings)

}