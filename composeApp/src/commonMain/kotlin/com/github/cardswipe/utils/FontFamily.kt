package com.github.cardswipe.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.github.cardswipe.resources.Res
import com.github.cardswipe.resources.poppins_bold
import com.github.cardswipe.resources.poppins_regular
import com.github.cardswipe.resources.poppins_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
fun appFontFamily(): FontFamily = FontFamily(
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold),
)
