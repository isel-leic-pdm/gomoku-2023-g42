package com.example.gomoku.lobby

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.user.User
import kotlinx.coroutines.launch

@Composable
fun LobbyScreen(
    onCreateLobby: (String?) -> Unit,
    getUser: () -> Unit,
    userInfo: IOState<User?>
) {

    if (userInfo is Idle) getUser()
    val token = if (userInfo is Loaded) userInfo.result.getOrNull()?.token else null
    val scope = rememberCoroutineScope()
    Column {
        Text("Waiting for opponent!")
    }
    Button(onClick = {
        scope.launch {
            onCreateLobby(token)
        }
    }) {
        Text(text = "oi")
    }
}