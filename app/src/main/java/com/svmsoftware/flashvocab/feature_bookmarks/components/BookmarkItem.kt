package com.svmsoftware.flashvocab.feature_bookmarks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.PastelBlue
import com.svmsoftware.flashvocab.core.domain.model.Bookmark

@Composable
fun BookmarkItem(
    item: Bookmark,
    textToSpeech: (String, String) -> Unit,
    modifier: Modifier
) {

    val targetUiLanguage = UiLanguage.byCode(item.targetLanguage)
    val sourceUiLanguage = UiLanguage.byCode(item.sourceLanguage)

    Column(
        modifier = modifier
            .background(MidnightBlue)
            .clip(RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(language = sourceUiLanguage)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = modifier.clickable {
                    textToSpeech(item.sourceText, sourceUiLanguage.language.langCode)
                },
                text = item.sourceText,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(language = targetUiLanguage)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = modifier.clickable {
                    textToSpeech(item.targetText, targetUiLanguage.language.langCode)
                },
                text = item.targetText,
                color = PastelBlue,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
