package com.svmsoftware.flashvocab.feature_home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svmsoftware.flashvocab.core.domain.model.UiEvent
import com.svmsoftware.flashvocab.feature_home.components.LanguageSelector
import com.svmsoftware.flashvocab.feature_home.components.SourceTextBox
import com.svmsoftware.flashvocab.feature_home.components.TargetTextBox
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.string
                    )
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            SourceTextBox(modifier = Modifier.fillMaxWidth(),
                value = state.source,
                language = state.sourceLanguage.language.langName,
                onSoundClick = {
                    viewModel.textToSpeech(
                        state.source,
                        state.sourceLanguage.language.langCode
                    )
                },
                onSearch = {
                    viewModel.processTranslate()
                },
                onValueChange = { s -> viewModel.onSourceTextValueChange(s) },
                onCleanClick = {
                    viewModel.cleanSourceText()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TargetTextBox(
                modifier = Modifier.fillMaxWidth(),
                value = state.target,
                language = state.targetLanguage.language.langName,
                onSoundClick = {
                    viewModel.textToSpeech(
                        state.target,
                        state.targetLanguage.language.langCode
                    )
                },
                isHomeStateChanged = state.isHomeStateChanged,
                onBookmarkClick = {
                    viewModel.saveTranslation()
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LanguageSelector(modifier = Modifier.fillMaxWidth(),
                sourceLanguage = state.sourceLanguage,
                targetLanguage = state.targetLanguage,
                updateLanguages = { x, y -> viewModel.updateLanguages(x, y) },
                switchLanguages = { viewModel.switchLanguages() })
        }
    }
}
