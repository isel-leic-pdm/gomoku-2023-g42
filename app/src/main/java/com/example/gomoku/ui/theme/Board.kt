package com.example.gomoku.ui.theme

import Cell
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.R
import com.example.gomoku.model.BOARD_DIM
import com.example.gomoku.model.Board
import com.example.gomoku.model.Player
import com.example.gomoku.model.add


val cellSize = 65.dp
val lineSize = (cellSize.value * 1.5).toFloat()

@Composable
fun BoardView(boardState: MutableState<Board>) {
    Column {
        // alterar para dependencia de boardState por ex: boardState/ BOARD_DIM
        repeat(BOARD_DIM) { r ->
            Row {
                repeat(BOARD_DIM) { c ->
                    val cell = Cell(r, c)
                    CellView(cell = cell) {
                        val updatedBoard = boardState.value.add(cell, boardState.value.turn)
                        boardState.value = updatedBoard
                    }

                }


            }
        }
    }

}

@Composable
fun CellView(
    cell: Cell,
    modifier: Modifier = Modifier.size(cellSize),
    onClick: () -> Unit
) {
    Box(modifier = modifier, Alignment.Center) {
        DrawLine(Pair(cell.rowIndex, cell.colIndex))
        Box(modifier = Modifier
            .size(cellSize / 4)
            .clickable(true) { onClick() }
            .background(Color.Transparent)) {
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






