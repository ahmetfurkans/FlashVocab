package com.svmsoftware.flashvocab.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyReset(
    val dailyNotificationCount: Int = 0,
    val lastResetTime: Long = 0,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)