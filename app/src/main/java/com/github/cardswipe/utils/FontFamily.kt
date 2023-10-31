package com.github.cardswipe.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.github.tindercardswipe.R

object FontFamily {
    val appFontFamily = FontFamily(
        fonts = listOf(
            Font(
                resId = R.font.poppins_bold,
                weight = FontWeight.W900,
            ),
            Font(
                resId = R.font.poppins_regular,
                weight = FontWeight.W900,
            ),
            Font(
                resId = R.font.poppins_semi_bold,
                weight = FontWeight.W700,
            ),
        )
    )
}