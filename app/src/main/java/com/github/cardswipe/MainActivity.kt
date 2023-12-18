package com.github.cardswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cardswipe.component.Hint
import com.github.cardswipe.component.ProfileCard
import com.github.cardswipe.component.ResetButton
import com.github.cardswipe.ui.theme.TinderCardSwipeTheme
import com.github.cardswipe.utils.FontFamily
import com.pratyush.swipeablecard.enums.Direction
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                val lastIndex = DummyProfile.list.lastIndex
                val currentIndex = rememberSaveable { mutableIntStateOf(0) }

                var hint by remember {
                    mutableStateOf("Try to swipe a card")
                }

                // List of Dummy profile card
                val profileList = remember { mutableListOf<Dummy>() }.apply {
                    addAll(DummyProfile.list)
                }

                val scope = rememberCoroutineScope()
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
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
                            profileList.reversed().forEachIndexed { _, profile ->
                                ProfileCard(
                                    profile = profile,
                                    onSwipe = {
                                        hint = "Swiped towards ${stringFrom(it)}"
                                        currentIndex.intValue++

                                        // remove swiped profile from list
                                        profileList.removeFirst()

                                        // Dismiss previous SnackBar
                                        val currentSnackBar = snackBarHostState.currentSnackbarData
                                        currentSnackBar?.dismiss()

                                        // Show Snack bar
                                        scope.launch {
                                            val result = snackBarHostState
                                                .showSnackbar(
                                                    message = "Undo swiped card",
                                                    actionLabel = "Undo",
                                                    duration = SnackbarDuration.Short
                                                )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                /* Handle undo action performed */
                                                currentIndex.intValue-- // Decrement index
                                                // add swiped profile to list
                                                profileList.add(0, profile)
                                            }
                                        }
                                    }
                                )
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
                        ResetButton(
                            onClick = {
                                currentIndex.intValue = 0
                                // Dismiss previous SnackBar
                                val currentSnackBar = snackBarHostState.currentSnackbarData
                                currentSnackBar?.dismiss()
                                profileList.clear()
                                profileList.addAll(DummyProfile.list)
                            },
                            text = "Reset"
                        )
                        Hint(text = hint)
                    }
                }
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


