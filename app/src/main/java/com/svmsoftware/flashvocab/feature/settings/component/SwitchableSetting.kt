package com.svmsoftware.flashvocab.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.design_system.theme.DarkSlateBlue
import com.svmsoftware.flashvocab.core.design_system.theme.VividBlue
import com.svmsoftware.flashvocab.core.model.Setting


@Composable
fun SwitchableSetting(
    modifier: Modifier = Modifier,
    setting: Setting,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = setting.name,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Switch(
                modifier = Modifier.scale(0.75f),
                checked = setting.switchAbleStatus,
                onCheckedChange = { status -> onCheckedChange(status) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = VividBlue,
                    uncheckedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = DarkSlateBlue
                )
            )
        }
    }
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(DarkSlateBlue)
    )
}

