package com.github.lib

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun SwipeableCard(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onCardRemoved: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer(
                rotationZ = offsetX / 20,
                alpha = 1 - abs(offsetX / 300f)
            )
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    if (offsetX > 300) {
                        onSwipeRight()
                    } else if (offsetX < -300) {
                        onSwipeLeft()
                    }
                },
                onDragStopped = {
                    if (offsetX > 300) {
                        onCardRemoved()
                    } else if (offsetX < -300) {
                        onCardRemoved()
                    } else {
                        // Snap the card back to its original position if not swiped off
                        offsetX = 0f
                    }
                }
            )
    ) {
        content(Modifier.fillMaxSize())
    }
}


@Composable
fun SwipeAnimationScreen() {
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
