package com.svmsoftware.flashvocab.feature_bookmarks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svmsoftware.flashvocab.feature_bookmarks.components.BookmarkItem
import com.svmsoftware.flashvocab.feature_bookmarks.components.EmptyNoteScreen
import com.svmsoftware.flashvocab.feature_bookmarks.components.SearchTextField
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: BookmarkViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current

    Column(
        modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            }
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp)) {
        SearchTextField(modifier = modifier.height(48.dp),
            text = viewModel.state.value.query,
            onValueChange = { viewModel.onSearchValueChange(it) },
            shouldShowHint = viewModel.state.value.query.isEmpty(),
            onSearch = { /*TODO*/ },
            onFocusChanged = {})
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.state.value.bookmarks.isEmpty()) {
            EmptyNoteScreen(modifier = Modifier.fillMaxWidth())
        } else {
            LazyColumn() {
                items(viewModel.state.value.bookmarks) {
                    val delete = SwipeAction(onSwipe = {
                        viewModel.deleteBookmarkItem(it)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Translation deleted",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.restoreBookmark()
                            }
                        }
                    }, icon = {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete chat",
                            modifier = Modifier.padding(16.dp),
                            tint = Color.White
                        )
                    }, background = Color.Red.copy(alpha = 0.5f), isUndo = true
                    )
                    SwipeableActionsBox(
                        modifier = Modifier, swipeThreshold = 200.dp, endActions = listOf(delete)
                    ) {
                        BookmarkItem(
                            item = it, textToSpeech = { text, langCode ->
                                viewModel.textToSpeech(
                                    text, langCode
                                )
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
