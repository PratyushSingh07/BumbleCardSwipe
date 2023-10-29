package com.github.tindercardswipe

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.lib.swipeableCard
import com.github.tindercardswipe.ui.theme.DummyProfile
import com.github.tindercardswipe.ui.theme.TinderCardSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                val lastIndex = DummyProfile.list.lastIndex
                val currentIndex = remember { mutableStateOf(0) }

                DummyProfile.list.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .swipeableCard(
                                onSwipe = {
                                    Log.d("CARD_SWIPED", "swipe the card")
                                },
                                onCardRemoved = {
                                    Toast
                                        .makeText(this, "Card Removed", Toast.LENGTH_SHORT)
                                        .show()
                                    currentIndex.value++
                                }
                            ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {

                        Text(text = it.name, modifier = Modifier.padding(16.dp))
                    }
                }
                if (currentIndex.value == lastIndex + 1) {
                    Text(text = "All Cards Swiped")
                }
            }
        }
    }
}
