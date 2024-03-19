package com.svmsoftware.flashvocab.core.domain.model

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val string: String) : UiEvent()
}