package com.github.tindercardswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.lib.SwipeAnimation
import com.github.tindercardswipe.ui.theme.TinderCardSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                SwipeAnimation()
            }
        }
    }
}
