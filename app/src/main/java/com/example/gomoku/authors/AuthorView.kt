package com.example.gomoku.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthorScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontFamily = FontFamily.Serif, text = "Vasco Branco, number -> 48259")
        Spacer(modifier = Modifier.width(10.dp))
        Text(fontFamily = FontFamily.Serif, text = "José Borges, number -> 48269")
        Spacer(modifier = Modifier.width(10.dp))
        Text(fontFamily = FontFamily.Serif, text = "Sérgio Capela, number -> 46080")
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { }) { Text(text = "Send a message to support the creators") }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthorScreenPrev(){
    AuthorScreen()
}

