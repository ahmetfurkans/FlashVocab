package com.svmsoftware.flashvocab.core.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Word(
    val name: String,
    val description: String,
    val date: LocalDate,
    val isExpanded: Boolean = false
)

// TODO Delete test object
@RequiresApi(Build.VERSION_CODES.O)
val testWord = Word(
    name = "Hello",
    description = "Lorem ipsum dolar text",
    date = LocalDate.now()
)