package com.svmsoftware.flashvocab.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage

@Entity
data class Bookmark(
    val originalText: String,
    val translatedText: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val time: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)