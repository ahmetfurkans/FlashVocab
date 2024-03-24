package com.svmsoftware.flashvocab.feature_home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.PastelBlue

@Composable
fun SourceTextBox(
    modifier: Modifier = Modifier,
    value: String = "",
    language: String = "",
    onValueChange: (String) -> Unit,
    onSearch: (() -> Unit)?,
    onSoundClick: () -> Unit,
    onCleanClick: () -> Unit,
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(MidnightBlue)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = language, style = MaterialTheme.typography.labelLarge, color = PastelBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            BasicTextField(
                value = value,
                onValueChange = { s -> onValueChange(s) },
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch?.let { it() }
                    defaultKeyboardAction(ImeAction.Search)
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                maxLines = 5,
                minLines = 5,
                textStyle = MaterialTheme.typography.bodyLarge.copy(Color.White),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    modifier = Modifier
                        .clickable { onCleanClick() },
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    tint = PastelBlue
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    modifier = Modifier
                        .clickable { onSoundClick() },
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = null,
                    tint = PastelBlue
                )
            }
        }
    }
}