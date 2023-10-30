package com.github.tindercardswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.github.tindercardswipe.component.ProfileCard
import com.github.tindercardswipe.ui.theme.TinderCardSwipeTheme
import com.github.tindercardswipe.utils.FontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                val lastIndex = DummyProfile.list.lastIndex
                val currentIndex = remember { mutableIntStateOf(0) }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DummyProfile.list.forEachIndexed { _, profile ->
                        ProfileCard(
                            profile = profile,
                            onRightSwipe = {
                                currentIndex.intValue++
                            },
                            onLeftSwipe = {
                                currentIndex.intValue++
                            },
                        )
                    }
                    if (currentIndex.intValue > lastIndex) {
                        Text(
                            text = "All Cards Swiped",
                            fontSize = 30.sp,
                            fontFamily = FontFamily.appFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


