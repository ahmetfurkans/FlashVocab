package com.svmsoftware.flashvocab.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.feature_setting.presentation.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {


    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    fun updateLanguages(sourceLanguage: UiLanguage?, targetLanguage: UiLanguage?) {
        sourceLanguage?.let {
            _state.value = state.value.copy(
                sourceLanguage = it
            )
        }
        targetLanguage?.let {
            _state.value = state.value.copy(
                targetLanguage = it
            )
        }
    }

    fun onSourceTextValueChange(sourceText: String) {
        _state.value = state.value.copy(
            source = sourceText
        )
    }

}
