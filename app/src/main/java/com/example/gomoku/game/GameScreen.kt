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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.demo.domain.BoardDraw
import com.example.demo.domain.BoardRun
import com.example.demo.domain.Player
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.ui.theme.GomokuTheme

@Composable
fun GameScreen(game: IOState<Either<Error, GameModel?>>,getGame:() -> Unit) {

    getGame()
    if (game is Loaded) {
        val game1 = game.result.getOrNull() as GameModel?
        val table = game1?.board
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
                when (table) {
                    is BoardRun -> {
                        (GameView(game))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Turn: ")
                            ShowTurn(turn = table.turn)
                        }
                    }

                    is BoardDraw -> TODO()
                    else -> TODO()
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
                        Text(text = ": White")
                        Spacer(modifier = Modifier.width(50.dp))
                        Image(
                            painter = painterResource(id = R.drawable.blackstone),
                            contentDescription = null,
                            Modifier.size(50.dp)
                        )
                        Text(text = ": Black")
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
        contentDescription = if (turn == Player.W) "White Stone" else "Black Stone",
        Modifier.size(30.dp)
    )

}


/*@Preview(showSystemUi = true)
@Composable
fun GameScreenPreview(){
    val board = remember {
        mutableStateOf<Board>(createBoard(Player.B, BoardSize.BIG.size, Rules.PRO.string(), Variant.FREESTYLE.string()))
    }
    GameScreen(board)
}*/