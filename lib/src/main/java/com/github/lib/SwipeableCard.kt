package com.github.lib

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
