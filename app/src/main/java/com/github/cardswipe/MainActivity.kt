package com.github.cardswipe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cardswipe.component.ProfileCard
import com.github.cardswipe.ui.theme.TinderCardSwipeTheme
import com.github.cardswipe.utils.FontFamily
import com.pratyush.swipeablecard.enums.Direction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderCardSwipeTheme {
                val lastIndex = DummyProfile.list.lastIndex
                val currentIndex = rememberSaveable { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        DummyProfile.list.forEachIndexed { _, profile ->
                            ProfileCard(
                                profile = profile,
                                onSwipe = {
                                    Log.d("DIRECTION", stringFrom(it))
                                    currentIndex.intValue++
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {},
                            modifier= Modifier.size(70.dp),
                            shape = CircleShape
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "right")
                        }
                        Button(
                            onClick = {},
                            modifier= Modifier.size(70.dp),
                            shape = CircleShape
                        ) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "right")
                        }
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


