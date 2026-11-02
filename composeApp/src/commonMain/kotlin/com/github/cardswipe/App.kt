package com.github.cardswipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cardswipe.component.Hint
import com.github.cardswipe.component.ProfileCard
import com.github.cardswipe.component.ResetButton
import com.github.cardswipe.ui.theme.TinderCardSwipeTheme
import com.github.cardswipe.utils.appFontFamily
import com.pratyush.swipeablecard.ExperimentalSwipeGestureApi
import com.pratyush.swipeablecard.SwipeableCardState
import com.pratyush.swipeablecard.enums.Direction
import com.pratyush.swipeablecard.swipeableCard
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSwipeGestureApi::class)
@Composable
fun App() {
    TinderCardSwipeTheme {
        val profiles = remember { DummyProfile.list.toMutableStateList() }
        val cardStates = remember { mutableMapOf<Dummy, SwipeableCardState>() }
        var hint by remember { mutableStateOf("Try to swipe a card") }
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }

        fun stateOf(profile: Dummy): SwipeableCardState =
            cardStates.getOrPut(profile) { SwipeableCardState() }

        fun onSwiped(profile: Dummy, direction: Direction) {
            hint = "Swiped towards ${stringFrom(direction)}"
            profiles.remove(profile)
            snackBarHostState.currentSnackbarData?.dismiss()
            scope.launch {
                val result = snackBarHostState.showSnackbar(
                    message = "Undo swiped card",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed && profile !in profiles) {
                    profiles.add(0, profile)
                    stateOf(profile).reset()
                }
            }
        }

        fun swipeTopCard(direction: Direction) {
            val profile = profiles.firstOrNull() ?: return
            scope.launch {
                if (stateOf(profile).swipe(direction)) onSwiped(profile, direction)
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(contentPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (profiles.isEmpty()) {
                        Text(
                            text = "All Cards Swiped",
                            fontSize = 26.sp,
                            fontFamily = appFontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }

                    val visible = profiles.take(3)
                    val topState = visible.firstOrNull()?.let { stateOf(it) }

                    for (depth in visible.indices.reversed()) {
                        val profile = visible[depth]
                        key(profile) {
                            ProfileCard(
                                profile = profile,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .graphicsLayer {
                                        val scale = when (depth) {
                                            0 -> 1f
                                            1 -> 0.95f + 0.05f * abs(topState?.progress ?: 0f)
                                            else -> 0.95f
                                        }
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    .swipeableCard(
                                        state = stateOf(profile),
                                        onSwipe = { onSwiped(profile, it) },
                                        enabled = depth == 0
                                    )
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ResetButton(
                        onClick = { swipeTopCard(Direction.LEFT) },
                        text = "👈 Nope"
                    )
                    ResetButton(
                        onClick = {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            cardStates.clear()
                            profiles.clear()
                            profiles.addAll(DummyProfile.list)
                        },
                        text = "Reset"
                    )
                    ResetButton(
                        onClick = { swipeTopCard(Direction.RIGHT) },
                        text = "Like 👉"
                    )
                }
                Hint(text = hint)
            }
        }
    }
}

private fun stringFrom(direction: Direction): String {
    return when (direction) {
        Direction.LEFT -> "Left 👈"
        Direction.RIGHT -> "Right 👉"
    }
}
