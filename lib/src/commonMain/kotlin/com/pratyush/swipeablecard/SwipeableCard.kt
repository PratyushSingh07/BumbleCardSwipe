package com.pratyush.swipeablecard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.pratyush.swipeablecard.enums.Direction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@ExperimentalSwipeGestureApi
@Stable
class SwipeableCardState {

    internal val offset = Animatable(Offset.Zero, Offset.VectorConverter)
    internal var size by mutableStateOf(IntSize.Zero)
    internal var tiltSign by mutableFloatStateOf(1f)

    var swipedDirection by mutableStateOf<Direction?>(null)
        private set

    val progress: Float
        get() = if (size.width == 0) 0f else (offset.value.x / size.width).coerceIn(-1f, 1f)

    suspend fun swipe(direction: Direction): Boolean {
        if (swipedDirection != null) return false
        swipedDirection = direction
        val sign = if (direction == Direction.RIGHT) 1f else -1f
        offset.animateTo(
            targetValue = Offset(sign * size.width * 1.5f, offset.value.y),
            animationSpec = tween(durationMillis = 250)
        )
        return true
    }

    suspend fun reset() {
        swipedDirection = null
        offset.animateTo(
            targetValue = Offset.Zero,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }
}

@ExperimentalSwipeGestureApi
fun Modifier.swipeableCard(
    state: SwipeableCardState,
    onSwipe: (Direction) -> Unit,
    enabled: Boolean = true,
    distanceThreshold: Float = 0.35f,
    velocityThreshold: Dp = 800.dp,
    maxRotation: Float = 15f,
): Modifier = this
    .onSizeChanged { state.size = it }
    .pointerInput(state, enabled) {
        if (!enabled) return@pointerInput
        val minFlingVelocity = velocityThreshold.toPx()
        val tracker = VelocityTracker()
        var dragPosition = Offset.Zero
        coroutineScope {
            detectDragGestures(
                onDragStart = { touch ->
                    if (state.swipedDirection == null) {
                        tracker.resetTracking()
                        dragPosition = state.offset.value
                        state.tiltSign = if (touch.y < size.height / 2f) 1f else -1f
                        launch { state.offset.stop() }
                    }
                },
                onDrag = { change, dragAmount ->
                    if (state.swipedDirection == null) {
                        change.consume()
                        dragPosition += dragAmount
                        tracker.addPosition(change.uptimeMillis, dragPosition)
                        val target = dragPosition
                        launch { state.offset.snapTo(target) }
                    }
                },
                onDragEnd = {
                    if (state.swipedDirection == null) {
                        val velocityX = tracker.calculateVelocity().x
                        val limit = size.width * distanceThreshold
                        val direction = when {
                            velocityX > minFlingVelocity -> Direction.RIGHT
                            velocityX < -minFlingVelocity -> Direction.LEFT
                            dragPosition.x > limit -> Direction.RIGHT
                            dragPosition.x < -limit -> Direction.LEFT
                            else -> null
                        }
                        launch {
                            if (direction == null) {
                                state.reset()
                            } else if (state.swipe(direction)) {
                                onSwipe(direction)
                            }
                        }
                    }
                },
                onDragCancel = {
                    if (state.swipedDirection == null) {
                        launch { state.reset() }
                    }
                }
            )
        }
    }
    .graphicsLayer {
        translationX = state.offset.value.x
        translationY = state.offset.value.y
        rotationZ = state.progress * maxRotation * state.tiltSign
    }
