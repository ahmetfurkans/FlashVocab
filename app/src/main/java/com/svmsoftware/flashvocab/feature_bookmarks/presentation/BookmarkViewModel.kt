package com.svmsoftware.flashvocab.feature_bookmarks.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmsoftware.flashvocab.core.domain.model.UiEvent
import com.svmsoftware.flashvocab.core.domain.model.UiLanguage
import com.svmsoftware.flashvocab.core.domain.use_cases.ProcessTranslate
import com.svmsoftware.flashvocab.core.util.Resource
import com.svmsoftware.flashvocab.feature_bookmarks.domain.model.Bookmark
import com.svmsoftware.flashvocab.feature_bookmarks.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.feature_setting.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
