package com.sudo_pacman.cashapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sudo_pacman.cashapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.figtree_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)

val FontName = FontFamily(
    Font(R.font.figtree_semi_bold, FontWeight.SemiBold),
    Font(R.font.figtree_medium, FontWeight.Medium),
)

val semiBold14 = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    fontFamily = FontName
)

val semiBold25 = TextStyle(
    fontSize = 25.sp,
    fontWeight = FontWeight.W600,
    fontFamily = FontName
)

val medium16 = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    fontFamily = FontName
)

val medium18 = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W500,
    fontFamily = FontName
)