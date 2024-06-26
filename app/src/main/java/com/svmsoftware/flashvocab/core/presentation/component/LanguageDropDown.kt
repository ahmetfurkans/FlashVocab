package com.svmsoftware.flashvocab.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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

@Composable
fun LanguageDropDown(
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language.language.langName,
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
}