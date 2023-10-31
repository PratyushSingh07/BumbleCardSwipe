package com.pratyush.swipeablecard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import com.pratyush.swipeablecard.enums.Direction
import kotlin.math.roundToInt

/**
 * Enables Bumble like swipe gesture
 *
 *  @param onSwipe will be called once a swipe gesture is completed. The given [Direction] will indicate which side the gesture was performed on
 *  @param enableSpringEffect enables a spring like effect if the card is released before it reaches the threshold. By default it is set to false
 */

@ExperimentalSwipeGestureApi
fun Modifier.swipeableCard(
    onSwipe: (Direction) -> Unit,
    enableSpringEffect: Boolean = false
): Modifier = composed {

    var offsetX by rememberSwipeableCardOffset(0f)

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    val offsetValue = if (enableSpringEffect) animatedOffsetX else offsetX

    Modifier
        .offset { IntOffset(offsetValue.roundToInt(), 0) }
        .graphicsLayer(
            translationX = offsetValue * 1.5f,
            rotationZ = offsetValue / 20
        )
        .draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                offsetX += delta
            },
            onDragStopped = {
                if (offsetX > 300) {
                    offsetX = 1000f
                    onSwipe(Direction.RIGHT)
                } else if (offsetX < -300) {
                    offsetX = -1000f
                    onSwipe(Direction.LEFT)
                } else {
                    // Snap the card back to its original position if not swiped off
                    offsetX = 0f
                }
            }
        )
}

@Composable
fun rememberSwipeableCardOffset(initialOffset: Float): MutableState<Float> {
    return rememberSaveable { mutableFloatStateOf(initialOffset) }
}
