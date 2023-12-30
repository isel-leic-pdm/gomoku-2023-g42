package com.example.gomoku.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Loaded


@Composable
fun LobbyScreen(
    onCreateLobby: () -> Unit,
    error: IOState<String>,
    onDismiss: () -> Unit = {}
) {

    onCreateLobby()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(id = R.string.waiting_opp))
        CircularProgressIndicator()
    }

    if (error is Loaded) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = stringResource(id = R.string.general_error_label)) },
            text = { Text(text = error.result.getOrNull() ?: stringResource(id = R.string.general_error_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok_button))
                }
            }
        )
    }
}