package com.svmsoftware.flashvocab.feature_bookmarks.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.domain.model.testWord
import com.svmsoftware.flashvocab.feature_bookmarks.presentation.components.ExpandableWord
import com.svmsoftware.flashvocab.feature_bookmarks.presentation.components.SearchTextField


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookmarksScreen(modifier: Modifier = Modifier) {

    var value by remember {
        mutableStateOf("")
    }

    val defaultList = listOf(testWord, testWord, testWord)

    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp)
    ) {
        SearchTextField(modifier = modifier.height(48.dp),
            text = value,
            onValueChange = { value = it },
            shouldShowHint = value.isEmpty(),
            onSearch = { /*TODO*/ },
            onFocusChanged = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn() {
            items(defaultList) {
                ExpandableWord(
                    word = it,
                    onToggleClick = { /*TODO*/ },
                    onUnSavedButtonClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }
    }

}