package com.svmsoftware.flashvocab.feature.bookmarks.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.design_system.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.design_system.theme.VividBlue
import com.svmsoftware.flashvocab.core.model.Word

@Composable
fun ExpandableWord(
    word: Word, onToggleClick: () -> Unit, onUnSavedButtonClick: () -> Unit, modifier: Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MidnightBlue)
            .padding(2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleClick() }
            .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = word.name, style = MaterialTheme.typography.bodyMedium, color = VividBlue)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = word.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        AnimatedVisibility(visible = word.isExpanded) {}
    }
}