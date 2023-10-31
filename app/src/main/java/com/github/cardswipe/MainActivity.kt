package com.github.cardswipe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.github.cardswipe.component.ProfileCard
import com.github.cardswipe.ui.theme.TinderCardSwipeTheme
import com.github.cardswipe.utils.FontFamily
import com.pratyush.swipeablecard.enums.Direction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                val lastIndex = DummyProfile.list.lastIndex
                val currentIndex = rememberSaveable { mutableIntStateOf(0) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    DummyProfile.list.forEachIndexed { _, profile ->
                        ProfileCard(
                            profile = profile,
                            onSwipe = {
                                Log.d("DIRECTION", stringFrom(it))
                                currentIndex.intValue++
                            }
                        )
                    }
                    if (currentIndex.intValue > lastIndex) {
                        Text(
                            text = "All Cards Swiped",
                            fontSize = 26.sp,
                            fontFamily = FontFamily.appFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun stringFrom(direction: Direction): String {
    return when (direction) {
        Direction.LEFT -> "Left 👈"
        Direction.RIGHT -> "Right 👉"
    }
}


