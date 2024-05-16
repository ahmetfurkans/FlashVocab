package com.svmsoftware.flashvocab.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage

@Composable
fun LanguageDropDownItem(
    language: UiLanguage, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    DropdownMenuItem(onClick = onClick, modifier = modifier, text = {
        Text(
            text = language.language.langName,
            color = Color.White,
            style = MaterialTheme.typography.displayMedium,
            )
    }, leadingIcon = {
        Image(
            painter = painterResource(id = language.drawableRes),
            contentDescription = language.language.langName,
            modifier = Modifier.size(40.dp)
        )
    })
}