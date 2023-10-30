package com.github.tindercardswipe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                            onRightSwipe = {
                                currentIndex.intValue++
                            },
                            onLeftSwipe = {
                                currentIndex.intValue++
                            },
                        )
                        Log.d("INDEX", currentIndex.intValue.toString())
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


