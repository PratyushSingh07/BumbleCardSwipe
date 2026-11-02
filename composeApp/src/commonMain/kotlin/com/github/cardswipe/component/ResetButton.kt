package com.github.cardswipe.component

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResetButton(
    onClick: () -> Unit, text: String
) {
    ElevatedButton(onClick = { onClick() }) {
        Text(text = text)
    }
}