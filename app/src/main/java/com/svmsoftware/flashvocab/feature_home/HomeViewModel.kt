package com.svmsoftware.flashvocab.feature_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.model.UiEvent
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.domain.model.UserSettings
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.repository.SettingRepository
import com.svmsoftware.flashvocab.core.domain.use_cases.ProcessTranslate
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeech
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeechManager
import com.svmsoftware.flashvocab.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val textToSpeechManager: TextToSpeechManager
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var processTranslateJob: Job? = null

    init {
        getTargetLanguage()
    }

    fun updateLanguages(uiLanguage: UiLanguage) {
        if (state.value.isSourceActive) {
            _state.value = state.value.copy(
                sourceLanguage = uiLanguage, isHomeStateChanged = true,
                isSourceActive = false
            )
        }
        if (state.value.isTargetActive) {
            _state.value = state.value.copy(
                targetLanguage = uiLanguage, isHomeStateChanged = true,
                isTargetActive = false
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
                targetLanguage = this.sourceLanguage,
                isHomeStateChanged = true
            )
        }
    }

    fun onSourceTextValueChange(sourceText: String) {
        _state.value = state.value.copy(
            source = sourceText, isHomeStateChanged = true
        )
        processTranslate()
    }

    fun processTranslate() {
        processTranslateJob?.cancel()
        if (state.value.source.isNotEmpty()) {
            processTranslateJob = viewModelScope.launch(Dispatchers.IO) {
                delay(500)
                val result = ProcessTranslate().invoke(
                    source = state.value.source,
                    targetLang = state.value.targetLanguage.language.langCode
                )

                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            target = result.data?.translatedText ?: "", isHomeStateChanged = true
                        )
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
        } else {
            cleanSourceText()
        }
    }

    fun textToSpeech(text: String, languageCode: String) {
        viewModelScope.launch {
            textToSpeechManager.shutdown()
            textToSpeechManager.speak(text, languageCode)
        }
    }

    fun saveTranslation() {
        viewModelScope.launch {
            if (processTranslateJob?.isActive == true) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        string = "Please wait until the translation process is complete before adding a bookmark!"
                    )
                )
            } else {
                val result = bookmarkRepository.insertBookmark(
                    Bookmark(
                        originalText = state.value.source,
                        translatedText = state.value.target,
                        targetLanguage = state.value.targetLanguage.language.langCode,
                        sourceLanguage = state.value.sourceLanguage.language.langCode,
                        time = System.currentTimeMillis(),
                    )
                )
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                string = result.desc ?: "Something went wrong!"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                string = "Translation saved bookmarks successfully!"
                            )
                        )
                        _state.value = state.value.copy(
                            isHomeStateChanged = false
                        )
                    }
                }
            }
        }
    }

    fun cleanSourceText() {
        _state.value = state.value.copy(
            target = "", source = ""
        )
    }

    fun triggerLanguageSelector(isSourceActive: Boolean, isTargetActive: Boolean) {
        _state.value = state.value.copy(
            isSourceActive = isSourceActive, isTargetActive = isTargetActive
        )
    }

    private fun getTargetLanguage() {
        viewModelScope.launch {

            settingRepository.getSettings().collect() {
                if (it == null) {
                    settingRepository.insertSetting(UserSettings())
                } else {
                    _state.value = state.value.copy(
                        targetLanguage = UiLanguage.byCode(it.translatedLangCode)
                    )
                }
            }
        }
    }
}
