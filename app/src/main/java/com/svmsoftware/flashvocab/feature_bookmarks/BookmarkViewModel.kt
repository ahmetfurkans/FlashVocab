package com.svmsoftware.flashvocab.feature_bookmarks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    private var recentlyDeletedBookmark: Bookmark? = null

    private var getBookmarkJob: Job? = null

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        getBookmarkJob?.cancel()
        getBookmarkJob = bookmarkRepository.getBookmarks().onEach { bookmarks ->
            val sortedList = bookmarks.sortedByDescending { it.time }
                _state.value = state.value.copy(
                    bookmarks = sortedList
                )
            }.launchIn(viewModelScope)
    }
}
