package com.example.gomoku.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.model.BoardRun
import com.example.gomoku.model.Player
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Loaded
import com.example.gomoku.model.PlayInputModel
import com.example.gomoku.ui.theme.GomokuTheme

@Composable
fun GameScreen(
    game: IOState<GameModel>,
    onPlay: (PlayInputModel) -> Unit = {},
    error: IOState<String>,
    onDismiss: () -> Unit = {}
) {
    var table by remember { mutableStateOf<GameModel?>(null) }
    if (game is Loaded) table = game.result.getOrNull() ?: table

    GomokuTheme {
        Button(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp))
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
            /*when (table) {
                is BoardRun -> {
                    drawBoard = true
                    (GameView(game) { input -> onPlay(input) })
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Turn: ")
                        ShowTurn(turn = (table as BoardRun).turn)
                    }
                }
                is BoardDraw -> Text(text = "Game ended it a draw") //TODO()
                is BoardWin -> Text(text = "Someone won") //TODO()
                else -> Text(text = "Loading game") //TODO()
            }*/
            if (table == null) {
                CircularProgressIndicator()
            }
            else {
                GameView(table) { input -> onPlay(input) }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.turn_label))
                    if(table?.board is BoardRun) ShowTurn(turn = (table?.board as BoardRun).turn)
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.whitestone),
                            contentDescription = null,
                            Modifier.size(50.dp)
                        )
                        Text(text = "${ (game as Loaded).result.getOrNull()?.playerW }")
                        Spacer(modifier = Modifier.width(50.dp))
                        Image(
                            painter = painterResource(id = R.drawable.blackstone),
                            contentDescription = null,
                            Modifier.size(50.dp)
                        )
                        Text(text = "${ (game as Loaded).result.getOrNull()?.playerW }")
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowTurn(turn: Player) {
    val imageResource = if (turn == Player.W) {
        painterResource(id = R.drawable.whitestone)
    } else {
        painterResource(id = R.drawable.blackstone)
    }
    Image(
        painter = imageResource,
        contentDescription = if (turn == Player.W) stringResource(id = R.string.w_stone) else stringResource(id = R.string.b_stone),
        Modifier.size(30.dp)
    )
}
