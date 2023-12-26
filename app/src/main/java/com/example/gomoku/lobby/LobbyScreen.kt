package com.example.gomoku.lobby

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LobbyScreen(onCreateLobby: () -> Unit) {
    val scope = rememberCoroutineScope()
    Column{
        Text("Waiting for opponent!")
    }
    Button(onClick = {
        scope.launch {
            onCreateLobby()
        }
    }) {
        Text(text = "oi")
    }
}