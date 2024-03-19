package com.svmsoftware.flashvocab.feature_setting.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.presentation.component.LanguageDropDown
import com.svmsoftware.flashvocab.core.presentation.theme.DarkSlateBlue

@Composable
fun LanguageSetting(
    modifier: Modifier = Modifier,
    isOpen: Boolean = false,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    uiLanguage: UiLanguage,
    onSelectLanguage: (UiLanguage) -> Unit
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
            LanguageDropDown(language = uiLanguage,
                isOpen = isOpen,
                onClick = { onClick() },
                onDismiss = { onDismiss() },
                onSelectLanguage = { onSelectLanguage(it); onDismiss() })
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

