package com.svmsoftware.flashvocab.feature_setting

import com.svmsoftware.flashvocab.core.domain.model.UserSettings

data class SettingState(
    val settings: UserSettings = UserSettings(),
    val isLanguageSelectorVisible: Boolean = false,
    val showBottomSheet: Boolean = true,
)

