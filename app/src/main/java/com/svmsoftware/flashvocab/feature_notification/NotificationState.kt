package com.svmsoftware.flashvocab.feature_notification

import com.svmsoftware.flashvocab.core.domain.model.UserSettings

data class NotificationState(
    val textToTranslate: String = "",
    val translatedText: String = "",
    val sourceLanguageCode: String = "",
    val targetLanguageCode: String = "",
    val userSettings: UserSettings? = null
)