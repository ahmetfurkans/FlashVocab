package com.svmsoftware.flashvocab.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.svmsoftware.flashvocab.core.model.Setting

@Composable
fun SettingRow(
    modifier: Modifier = Modifier, setting: Setting, onClickSetting: () -> Unit
) {
    Row (
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = setting.name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}


val testSettingSwitchable = Setting(
    name = "Notification", isSwitchable = true, switchAbleStatus = true
)