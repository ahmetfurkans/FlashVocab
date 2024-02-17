package com.svmsoftware.flashvocab.core.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.svmsoftware.flashvocab.R

val Barlow = FontFamily(
    Font(R.font.barlow, FontWeight.Normal),
    Font(R.font.barlow_medium, FontWeight.Medium),
    Font(R.font.barlow_semibold, FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = (20.4).sp
    ),
    labelMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (20.4).sp
    ),

)