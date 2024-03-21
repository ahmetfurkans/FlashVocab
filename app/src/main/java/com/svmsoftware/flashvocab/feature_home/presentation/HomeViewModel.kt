package com.svmsoftware.flashvocab.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.UiEvent
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.domain.use_cases.ProcessTranslate
import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.feature_setting.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : ViewModel() {


    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var processTranslateJob: Job? = null

    init {
        getTargetLanguage()
    }


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
        processTranslate()
    }

    fun switchLanguages() {
        state.value.apply {
            _state.value = this.copy(
                source = this.target,
                target = this.source,
                sourceLanguage = this.targetLanguage,
                targetLanguage = this.sourceLanguage
            )
        }
    }

    fun onSourceTextValueChange(sourceText: String) {
        _state.value = state.value.copy(
            source = sourceText
        )
        processTranslate()
    }

    fun processTranslate() {
        processTranslateJob?.cancel()

        processTranslateJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val result = ProcessTranslate().invoke(
                source = state.value.source,
                targetLang = state.value.targetLanguage.language.langCode,
                sourceLang = state.value.sourceLanguage.language.langCode
            )

            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        target = result.data ?: ""
                    )
                    println("Counter" + state.value.source)
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            string = result.desc ?: "Something went wrong!"
                        )
                    )
                }
            }
        }
    }

    private fun getTargetLanguage() {
        viewModelScope.launch {
            settingRepository.getSettings().collectLatest {
                _state.value = state.value.copy(
                    targetLanguage = UiLanguage.byCode("en")
                )
            }
        }
    }
}
