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
    private val textToSpeech: TextToSpeech
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
        if (state.value.source.isNotEmpty()) {
            processTranslateJob = viewModelScope.launch(Dispatchers.IO) {
                delay(100)
                val result = ProcessTranslate().invoke(
                    source = state.value.source,
                    targetLang = state.value.targetLanguage.language.langCode
                )

                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            target = result.data?.translatedText ?: ""
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

    fun textToSpeech() {
        textToSpeech.invoke(state.value.target)
    }

    fun saveTranslation() {
        viewModelScope.launch {
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
                }
            }
        }
    }

    fun cleanSourceText() {
        _state.value = state.value.copy(
            target = "", source = ""
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
