package com.svmsoftware.flashvocab.core.model

data class Setting(
    val name: String,
    val isSwitchable: Boolean = false,
    val status: String? = null,
    val switchAbleStatus: Boolean = false
)

val defaultSettings = listOf(
    Setting("I speak", status = "English", isSwitchable = false),
    Setting("Notification", switchAbleStatus = false, isSwitchable = true),
    Setting("Plan", status = "Free", isSwitchable = false),
    Setting("Auto save", switchAbleStatus = true, isSwitchable = true),
    Setting("Auto read", switchAbleStatus = true, isSwitchable = true),
    Setting("Support", status = "Contact us", isSwitchable = false),
    Setting("Guide", status = "How to use", isSwitchable = false)
)