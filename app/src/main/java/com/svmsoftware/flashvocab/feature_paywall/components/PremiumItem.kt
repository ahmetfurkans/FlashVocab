package com.svmsoftware.flashvocab.feature_paywall.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.presentation.theme.PastelBlue
import com.svmsoftware.flashvocab.core.presentation.theme.VividBlue

@Composable
fun PremiumItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .wrapContentSize(),
        colors = CardDefaults.cardColors(MidnightBlue)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "1 month", style = MaterialTheme.typography.bodyMedium, color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "3$", style = MaterialTheme.typography.bodyMedium, color = PastelBlue
            )
            Text(
                text = "3$ per month",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            TextButton(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.wrapContentSize(),
                onClick = { TODO() },
                colors = ButtonDefaults.buttonColors(containerColor = VividBlue)
            ) {
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .wrapContentSize(),
                    text = "Save 0%", color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }
    }
}