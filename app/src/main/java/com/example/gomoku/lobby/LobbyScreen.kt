package com.example.gomoku.lobby

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LobbyScreen(onCreateLobby: () -> Unit) {
    onCreateLobby()
    Column{
        Text("Waiting for opponent!")
    }
}