package com.nttdata.androidtv.ui.theme

import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Typography
import com.nttdata.androidtv.R

val CatchEye = FontFamily(
    Font(R.font.catchye_regular, FontWeight.Normal, FontStyle.Normal))

val Belgiano = FontFamily(
    Font(R.font.belgiano_regular, FontWeight.Normal, FontStyle.Normal))

val Typography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
        color = OnSurface,
        fontFamily = CatchEye
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        color = OnBackground
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
        color = OnSurface,
        fontFamily = CatchEye
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        color = OnSurface
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp,
        color = OnSurface,
        fontFamily = Belgiano
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = OnSurface
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = OnSurface,
        fontFamily = Belgiano
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Primary
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = CatchEye,
        color = Primary
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = OnSurface,
        fontFamily = Belgiano
    )
)