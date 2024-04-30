package com.svmsoftware.flashvocab.feature_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.presentation.component.LanguageDropDown
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.VividBlue

@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    sourceLanguage: UiLanguage,
    targetLanguage: UiLanguage,
    updateLanguages: (UiLanguage?, UiLanguage?) -> Unit,
    switchLanguages: () -> Unit,
) {

    var sourceLanguageIsOpen by remember {
        mutableStateOf(false)
    }

    var targetLanguageIsOpen by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = modifier.clip(RoundedCornerShape(30.dp)),
            colors = CardDefaults.cardColors(MidnightBlue),
        ) {

            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LanguageDropDown(language = sourceLanguage,
                    isOpen = sourceLanguageIsOpen,
                    onClick = {
                        sourceLanguageIsOpen = true
                        targetLanguageIsOpen = false
                    },
                    onDismiss = { sourceLanguageIsOpen = false },
                    onSelectLanguage = { updateLanguages(it, null); sourceLanguageIsOpen = false })
                LanguageDropDown(language = targetLanguage,
                    isOpen = targetLanguageIsOpen,
                    onClick = {
                        targetLanguageIsOpen = true
                        sourceLanguageIsOpen = false
                    },
                    onDismiss = { targetLanguageIsOpen = false },
                    onSelectLanguage = { updateLanguages(null, it); targetLanguageIsOpen = false })
            }
        }
        Box(modifier = Modifier
            .background(VividBlue, shape = RoundedCornerShape(50))
            .padding(18.dp)
            .clickable { switchLanguages() }) {
            Icon(
                imageVector = Icons.Default.Repeat, contentDescription = null, tint = Color.White
            )
        }
    }
}