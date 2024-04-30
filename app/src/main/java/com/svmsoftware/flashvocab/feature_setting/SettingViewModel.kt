package com.svmsoftware.flashvocab.feature_setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.UserSettings
import com.svmsoftware.flashvocab.core.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
                if (it == null) {
                    updateSettings(UserSettings())
                } else {
                    _state.value = state.value.copy(
                        settings = it
                    )
                }
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

    fun toggleLanguageSelector(isOpen: Boolean) {
        _state.value = state.value.copy(
            isLanguageSelectorVisible = isOpen
        )
    }
}
