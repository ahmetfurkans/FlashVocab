package com.svmsoftware.flashvocab.feature_bookmarks.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage

@Entity
data class Bookmark(
    val sourceText: String,
    val targetText: String,
    val sourceLanguage: UiLanguage,
    val targetLanguage: UiLanguage,
    val time: Long,
    @PrimaryKey val id: Int? = null
)