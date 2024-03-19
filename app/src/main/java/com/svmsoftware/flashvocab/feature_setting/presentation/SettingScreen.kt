package com.svmsoftware.flashvocab.feature_setting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svmsoftware.flashvocab.R
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.feature_setting.presentation.components.CardButton
import com.svmsoftware.flashvocab.feature_setting.presentation.components.LanguageSetting
import com.svmsoftware.flashvocab.feature_setting.presentation.components.StatusSetting
import com.svmsoftware.flashvocab.feature_setting.presentation.components.SwitchableSetting

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier, viewModel: SettingViewModel = hiltViewModel(),
) {

    val settings = viewModel.state.value.settings
    val isLanguageSelectorVisible = viewModel.state.value.isLanguageSelectorVisible

    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                LanguageSetting(uiLanguage = UiLanguage.byCode(settings.translatedLangCode),
                    isOpen = isLanguageSelectorVisible,
                    onSelectLanguage = { viewModel.updateSettings(settings.copy(translatedLangCode = it.language.langCode)) },
                    onClick = { viewModel.toggleLanguageSelector(true) },
                    onDismiss = { viewModel.toggleLanguageSelector(false) })
            }
            item {
                SwitchableSetting(title = "Notification",
                    status = settings.isNotificationEnabled,
                    onCheckedChange = {
                        viewModel.updateSettings(settings.copy(isNotificationEnabled = it))
                    })
            }
            item { StatusSetting(title = "Plan", status = "Free") {} }
            item {
                SwitchableSetting(title = "Auto Read",
                    status = settings.isAutoReadEnabled,
                    onCheckedChange = {
                        viewModel.updateSettings(settings.copy(isAutoReadEnabled = it))
                    })
            }
            item {
                SwitchableSetting(title = "Auto Save",
                    status = settings.isAutoSaveEnabled,
                    onCheckedChange = {
                        viewModel.updateSettings(settings.copy(isAutoSaveEnabled = it))
                    })
            }
            item { StatusSetting(title = "Support", status = "Contact us") {} }
            item { StatusSetting(title = "Guide", status = "How to use") {} }
            item {
                CardButton(
                    title = stringResource(R.string.setting_screen_card_button_title),
                    description = stringResource(
                        R.string.setting_screen_card_button_description
                    ),
                    buttonTitle = stringResource(R.string.clear)
                ) {}
            }
        }
    }
}