package com.svmsoftware.flashvocab.feature_home

import com.svmsoftware.flashvocab.core.domain.model.UiLanguage

data class HomeState(
    val source: String = "",
    val target: String = "",
    val sourceLanguage: UiLanguage = UiLanguage.byCode("en"),
    val targetLanguage: UiLanguage = UiLanguage.byCode("tr"),
)