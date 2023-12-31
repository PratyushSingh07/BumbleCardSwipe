package com.github.cardswipe.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cardswipe.Dummy
import com.github.cardswipe.utils.FontFamily
import com.pratyush.swipeablecard.ExperimentalSwipeGestureApi
import com.pratyush.swipeablecard.enums.Direction
import com.pratyush.swipeablecard.swipeableCard

@OptIn(ExperimentalSwipeGestureApi::class)
@Composable
fun ProfileCard(
    profile: Dummy,
    onSwipe: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
        ),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .swipeableCard(
                onSwipe = onSwipe,
                enableSpringEffect = true
            ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = profile.name,
                modifier = Modifier.padding(16.dp),
                fontFamily = FontFamily.appFontFamily,
                textAlign = TextAlign.Center,
                fontSize = 200.sp,
                color = Color.White
            )
        }
    }
}