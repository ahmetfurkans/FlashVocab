package com.svmsoftware.flashvocab.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.feature.settings.component.SettingRow
import com.svmsoftware.flashvocab.feature.settings.component.testSettingSwitchable

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SettingRow(setting = testSettingSwitchable) {

        }
    }
}