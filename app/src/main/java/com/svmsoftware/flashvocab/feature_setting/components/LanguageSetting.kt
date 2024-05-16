package com.svmsoftware.flashvocab.feature_setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.presentation.theme.DarkSlateBlue

@Composable
fun LanguageSetting(
    modifier: Modifier = Modifier,
    isOpen: Boolean = false,
    onClick: () -> Unit,
    uiLanguage: UiLanguage,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "I speak",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(onClick = onClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiLanguage.language.langName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Icon(
                    imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (isOpen) {
                        "open"
                    } else {
                        "close"
                    },
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(DarkSlateBlue)
        )
    }
}

