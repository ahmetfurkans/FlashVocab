package com.svmsoftware.flashvocab.core.model

data class Setting(
    val name: String,
    val isSwitchable: Boolean = false,
    val status: String? = null,
    val switchAbleStatus: Boolean? = null
)
