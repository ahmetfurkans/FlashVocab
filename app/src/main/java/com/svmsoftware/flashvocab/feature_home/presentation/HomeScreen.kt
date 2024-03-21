package com.svmsoftware.flashvocab.feature_home.presentation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svmsoftware.flashvocab.core.domain.model.UiEvent
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.VividBlue
import com.svmsoftware.flashvocab.feature_home.presentation.components.LanguageSelector
import com.svmsoftware.flashvocab.feature_home.presentation.components.TextInputField
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(vertical = 36.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TextInputField(modifier = Modifier.fillMaxWidth(),
                    background = MidnightBlue,
                    value = state.source,
                    language = state.sourceLanguage.language.langName,
                    onSoundClick = {},
                    onSearch = {
                        viewModel.processTranslate()
                    },
                    onValueChange = { s -> viewModel.onSourceTextValueChange(s) })
                Spacer(modifier = Modifier.height(32.dp))
                TextInputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelColor = Color.White,
                    background = VividBlue,
                    readOnly = true,
                    value = state.target,
                    language = state.targetLanguage.language.langName,
                    onSoundClick = {
                        viewModel.processTranslate()
                    },
                    onValueChange = null,
                    onSearch = null
                )
                Spacer(modifier = Modifier.height(32.dp))
                LanguageSelector(modifier = Modifier.fillMaxWidth(),
                    sourceLanguage = state.sourceLanguage,
                    targetLanguage = state.targetLanguage,
                    updateLanguages = { x, y -> viewModel.updateLanguages(x, y) },
                    switchLanguages = { viewModel.switchLanguages() })
            }
        }
    }
}
