package com.github.lib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwipeAnimation() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val cardList = listOf(
        "Card 1 Content",
        "Card 2 Content",
        "Card 3 Content"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentIndex < cardList.size) {
            SwipeableCard(
                onSwipeLeft = {
                    if (currentIndex < cardList.size - 1) {
                        currentIndex++
                    }
                },
                onSwipeRight = {
                    if (currentIndex < cardList.size - 1) {
                        currentIndex++
                    }
                },
                onCardRemoved = {
                    currentIndex++
                }
            ) { modifier ->
                Card(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(text = cardList[currentIndex], modifier = Modifier.padding(16.dp))
                }
            }
        } else {
            Text(text = "All cards swiped!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
