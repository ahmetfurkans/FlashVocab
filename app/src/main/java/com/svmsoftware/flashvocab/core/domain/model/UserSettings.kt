package com.svmsoftware.flashvocab.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserSettings(
    val translatedLangCode: String = "en",
    val isNotificationEnabled: Boolean = false,
    val paymentPlan: String = "Free",
    val isAutoReadEnabled: Boolean = false,
    val isAutoSaveEnabled: Boolean = false,
    @PrimaryKey val id: Int? = null
)