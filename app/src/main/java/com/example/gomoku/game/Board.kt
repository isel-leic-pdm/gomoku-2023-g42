package com.example.gomoku.game

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.model.Board
import com.example.gomoku.model.BoardRun
import com.example.gomoku.model.Player
import com.example.gomoku.model.Position
import com.example.gomoku.R
import com.example.gomoku.model.PlayInputModel

val cellSize = 20.dp
val lineSize = (cellSize.value * 1.5).toFloat()

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameView(game: GameModel?, onPlay: (PlayInputModel) -> Unit = {}) {
    if (game != null) {
        val boardSize = game.boardSize
        Position.Factory(boardSize).createPositions()
        Column {
            repeat(boardSize) { r ->
                Row {
                    repeat(boardSize) { c ->
                        val cell = Position(r, c, boardSize)
                        if (game.board is BoardRun) {
                            CellView(
                                cell = cell,
                                turn = game.board.turn,
                                board = game.board,
                            ) {
                                val input = PlayInputModel(r, c)
                                onPlay(input)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CellView(
    cell: Position,
    turn: Player?,
    board: Board,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.size(cellSize), Alignment.Center) {
        val piece = board.moves.filter { it.key.toString() == cell.toString() }.values
        val center =
            cell.row.index == board.size / 2 && cell.col.index == board.size / 2 && piece.isEmpty()
        DrawLine(Pair(cell.row.index, cell.col.index), board.size)
        Box(
            modifier = Modifier
                .size(cellSize)
                .clickable(piece.isEmpty(), onClick = { onClick() })
                .background(if (center) Color.Red else Color.Transparent)
        ) {
            if (piece.isNotEmpty()) {
                val imageResource =
                    if (piece.first() == Player.W) {
                        painterResource(id = R.drawable.whitestone)
                    } else {
                        painterResource(id = R.drawable.blackstone)
                    }
                Image(
                    painter = imageResource,
                    contentDescription = if (turn == Player.W) "White Stone" else "Black Stone"
                )
            }
        }
    }
}

@Composable
fun DrawLine(cord: Pair<Int, Int>, size: Int) {
    when (cord) {
        Pair(0, 0) -> LeftUpCorner()
        Pair(0, size - 1) -> RightUpCorner()
        Pair(size - 1, 0) -> LeftDownCorner()
        Pair(size - 1, size - 1) -> RightDownCorner()
        else -> {
            if (cord.first == 0) {
                UpWall()
                return
            }
            if (cord.second == 0) {
                LeftWall()
                return
            }
            if (cord.first == size - 1) {
                DownWall()
                return
            }
            if (cord.second == size - 1) {
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






