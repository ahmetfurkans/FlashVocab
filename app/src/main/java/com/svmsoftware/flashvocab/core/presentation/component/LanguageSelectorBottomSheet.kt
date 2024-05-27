package com.svmsoftware.flashvocab.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlack
import com.svmsoftware.flashvocab.core.presentation.theme.MidnightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectorBottomSheet(
    showBottomSheet: Boolean = true,
    onItemSelected: (UiLanguage) -> Unit,
    onDismissBottomSheet: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MidnightBlack, onDismissRequest = {
                onDismissBottomSheet()
            }, sheetState = sheetState
        ) {
            // Sheet content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .background(MidnightBlack)
            ) {
                items(UiLanguage.allLanguages) {
                    LanguageTab(language = it, onItemSelected = {
                        onItemSelected(it)
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
fun LanguageTab(
    modifier: Modifier = Modifier, language: UiLanguage, onItemSelected: (UiLanguage) -> Unit
) {
    Row(
        modifier
            .background(MidnightBlue)
            .clickable { onItemSelected(language) }
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = language.drawableRes),
            contentDescription = language.language.langName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Text(
            text = language.language.langName,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}