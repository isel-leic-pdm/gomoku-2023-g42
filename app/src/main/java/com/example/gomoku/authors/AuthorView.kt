package com.example.gomoku.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily

@Composable
fun AuthorScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontFamily = FontFamily.Serif, text = "Vasco Branco, number -> 48259")
        Text(fontFamily = FontFamily.Serif, text = "José Borges, number -> 48269")
        Text(fontFamily = FontFamily.Serif, text = "Sérgio Capela, number -> 46080")
        Button(onClick = { }) { Text(text = "Send a message to support the creators") }
    }
}
