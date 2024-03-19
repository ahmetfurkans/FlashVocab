package com.svmsoftware.flashvocab.feature_setting.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.feature_setting.domain.model.UserSettings
import com.svmsoftware.flashvocab.feature_setting.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    init {
        getSettings()
    }

    private val _state = mutableStateOf(SettingState())
    val state: State<SettingState> = _state

    private fun getSettings() {
        viewModelScope.launch {
            settingRepository.getSettings().collect {
                _state.value = state.value.copy(
                    settings = it
                )
            }
        }
    }

    fun updateSettings(userSettings: UserSettings) {
        viewModelScope.launch {
            settingRepository.insertSetting(
                userSettings
            )
        }
    }

    fun updateTranslatedLang(uiLanguage: UiLanguage) {
        viewModelScope.launch {
            updateSettings(
                state.value.settings.copy(translatedLangCode = uiLanguage.language.langCode)
            )
            _state.value = state.value.copy(
                isLanguageSelectorVisible = false
            )
        }
    }

    fun toggleLanguageSelector(isOpen: Boolean) {
        _state.value = state.value.copy(
            isLanguageSelectorVisible = isOpen
        )
    }
}
