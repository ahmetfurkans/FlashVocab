package com.svmsoftware.flashvocab.feature_home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SourceTextBox(modifier = Modifier.fillMaxWidth(),
                value = state.source,
                language = state.sourceLanguage.language.langName,
                onSoundClick = {},
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
                },
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