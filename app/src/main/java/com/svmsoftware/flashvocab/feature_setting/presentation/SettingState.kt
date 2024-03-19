package com.svmsoftware.flashvocab.feature_setting.presentation

import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings

data class SettingState(
    val settings: UserSettings = UserSettings(), val isLanguageSelectorVisible: Boolean = false
)

