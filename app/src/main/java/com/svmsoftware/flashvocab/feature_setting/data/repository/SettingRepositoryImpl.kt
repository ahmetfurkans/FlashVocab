package com.svmsoftware.flashvocab.feature_setting.data.repository

import com.svmsoftware.flashvocab.feature_setting.data.local.SettingDao
import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings
import com.svmsoftware.flashvocab.feature_setting.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(
    private val dao: SettingDao
) : SettingRepository {
    override fun getSettings(): Flow<UserSettings> {
        return dao.getSettings()
    }

    override suspend fun insertSetting(settings: UserSettings) {
        dao.updateSettings(setting = settings)
    }

}