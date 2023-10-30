package com.github.tindercardswipe.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.lib.swipeableCard
import com.github.tindercardswipe.Dummy
import com.github.tindercardswipe.utils.FontFamily

@Composable
fun ProfileCard(
    profile: Dummy,
    onRightSwipe: () -> Unit,
    onLeftSwipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .swipeableCard(
                onRightSwipe = onRightSwipe,
                onLeftSwipe = onLeftSwipe,
                enableSpringEffect = true
            ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Text(
            text = profile.name,
            modifier = Modifier.padding(16.dp),
            fontFamily = FontFamily.appFontFamily,
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
    }
}