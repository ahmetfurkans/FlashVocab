package com.svmsoftware.flashvocab.feature_bookmarks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.R

@Composable
fun EmptyNoteScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.home_bg_empty_list), contentDescription = null
        )
        Spacer(modifier = Modifier.run { height(16.dp) })
        Text(
            text = "It\'s Empty",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Hmm.. looks like you don\'t have any bookmarks",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}