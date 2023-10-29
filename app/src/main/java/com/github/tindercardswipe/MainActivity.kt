package com.github.tindercardswipe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.lib.swipeableCard
import com.github.tindercardswipe.ui.theme.TinderCardSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .swipeableCard(
                            onSwipe = {
                                Log.d("CARD_SWIPED", "swipe the card")
                            },
                            onCardRemoved = {
                                    Log.d("CARD_REMOVED", "remove the card")
                            }
                        ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(text = "Hello", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
