package com.example.gomoku.ui.theme


import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demo.domain.BOARD_DIM
import com.example.demo.domain.Board
import com.example.demo.domain.BoardRun
import com.example.demo.domain.Player
import com.example.demo.domain.Position
import com.example.demo.domain.createBoard
import com.example.gomoku.R

val cellSize = 20.dp
val lineSize = (cellSize.value * 1.5).toFloat()

//TODO rever esta function
@SuppressLint("UnrememberedMutableState")
@Composable
fun BoardView(boardState: MutableState<BoardRun>) {
    Column {
        // alterar para dependencia de boardState por ex: boardState/ BOARD_DIM
        //val t = mutableStateOf(null)
        repeat(BOARD_DIM) { r ->
            Row {
                repeat(BOARD_DIM) { c ->
                    val cell = Position(r, c)
                    CellView(cell = cell, turn = boardState.value.turn, board = boardState.value) {
                        val updatedBoard =
                            boardState.value.turn.let {
                                boardState.value.play(
                                    cell,
                                    it
                                )
                            }
                        boardState.value = updatedBoard as BoardRun
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BoardViewPreview(){
    val board = remember {
        mutableStateOf(createBoard(Player.BLACK))
    }
    BoardView(board)
}

@Composable
fun CellView(
    cell: Position,
    turn: Player?,
    board: Board,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.size(cellSize), Alignment.Center) {
        val piece = board.moves[cell]
        DrawLine(Pair(cell.rowIndex, cell.colIndex))
        Box(
            modifier = Modifier
                .size(cellSize )
                .clickable(piece == null, onClick = { onClick() })
                .background(Color.Transparent)
        ) {
            if (piece != null) {
                val imageResource = if (piece == Player.WHITE) {
                    painterResource(id = R.drawable.whitestone)
                } else {
                    painterResource(id = R.drawable.blackstone)
                }
                Image(
                    painter = imageResource,
                    contentDescription = if (turn == Player.WHITE) "White Stone" else "Black Stone"
                )
            }

        }

    }

}


@Composable
fun DrawLine(cord: Pair<Int, Int>) {
    when (cord) {
        Pair(0, 0) -> LeftUpCorner()
        Pair(0, BOARD_DIM - 1) -> RightUpCorner()
        Pair(BOARD_DIM - 1, 0) -> LeftDownCorner()
        Pair(BOARD_DIM - 1, BOARD_DIM - 1) -> RightDownCorner()
        else -> {
            if (cord.first == 0) {
                UpWall()
                return
            }
            if (cord.second == 0) {
                LeftWall()
                return
            }
            if (cord.first == BOARD_DIM - 1) {
                DownWall()
                return
            }
            if (cord.second == BOARD_DIM - 1) {
                RightWall()
                return
            } else {
                InnerWall()
                return
            }
        }
    }
}

@Composable
fun Up() {
    //segmento vertical superior
    Canvas(Modifier) {
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(0f, -lineSize),
            color = Color.Black,
            strokeWidth = 5F,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun Down() {
    //desenha o segmento vertical inferior
    Canvas(Modifier) {
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(0f, lineSize),
            color = Color.Black,
            strokeWidth = 5F,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun Left() {
    //desenha segmento horizontal à esquerda
    Canvas(Modifier) {
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(-lineSize, 0f),
            color = Color.Black,
            strokeWidth = 5F,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun Right() {
    //desenha segmento horizontal à direita
    Canvas(Modifier) {
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(lineSize, 0f),
            color = Color.Black,
            strokeWidth = 5F,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun RightDownCorner() {
    Up()
    Left()
}

@Composable
fun LeftDownCorner() {
    Up()
    Right()
}

@Composable
fun RightUpCorner() {
    Down()
    Left()
}

@Composable
fun LeftUpCorner() {
    Down()
    Right()
}

@Composable
fun InnerWall() {
    Up()
    Down()
    Left()
    Right()
}

@Composable
fun RightWall() {
    Up()
    Down()
    Left()
}

@Composable
fun DownWall() {
    Left()
    Up()
    Right()
}

@Composable
fun UpWall() {
    Left()
    Right()
    Down()
}

@Composable
fun LeftWall() {
    Up()
    Down()
    Right()
}






