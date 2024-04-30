package com.svmsoftware.flashvocab.feature_setting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.VividBlue

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    buttonTitle: String,
    onClickButton: () -> Unit
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(MidnightBlue),
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClickButton() },
                colors = ButtonDefaults.buttonColors(containerColor = VividBlue)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = buttonTitle, color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }
    }
}
