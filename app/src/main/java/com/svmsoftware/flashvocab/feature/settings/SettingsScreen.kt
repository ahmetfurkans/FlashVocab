package com.svmsoftware.flashvocab.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.R
import com.svmsoftware.flashvocab.core.model.defaultSettings
import com.svmsoftware.flashvocab.feature.settings.component.CardButton
import com.svmsoftware.flashvocab.feature.settings.component.StatusSetting
import com.svmsoftware.flashvocab.feature.settings.component.SwitchableSetting

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(defaultSettings) { setting ->
                if (setting.isSwitchable) {
                    SwitchableSetting(setting = setting, onCheckedChange = {})
                } else {
                    StatusSetting(setting = setting, onClickSetting = {})
                }
            }
            item {
                CardButton(
                    title = stringResource(R.string.setting_screen_card_button_title),
                    description = stringResource(
                        R.string.setting_screen_card_button_description
                    ),
                    buttonTitle = stringResource(R.string.clear)
                ) {

                }
            }
        }
    }
}