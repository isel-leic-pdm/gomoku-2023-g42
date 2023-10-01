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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.gomoku.model.BOARD_DIM
import com.example.gomoku.model.Player


val cellSize = 65.dp
val lineSize = (cellSize.value * 1.5).toFloat()
@Composable
fun boardView(boardState: Map<Cell, Player?>) {
    Column{
        // alterar para dependencia de boardState por ex: boardState/ BOARD_DIM
        repeat(BOARD_DIM){ r->
            Row {
                repeat( BOARD_DIM){ c->
                    val cell = Cell(r,c)
                    CellView(cell = cell)
            }



            }
        }
    }

}

@Composable
fun CellView(
    cell: Cell,
    modifier: Modifier = Modifier.size(cellSize))
        {
    Box(modifier = modifier, Alignment.Center) {
        drawLine(Pair(cell.rowIndex, cell.colIndex))
        Box(modifier = Modifier
            .size(cellSize / 4)
            .clickable(true) { {} }
            .background(Color.Transparent)) {
        }


    }
}
@Composable
fun drawLine (cord:Pair<Int,Int>) {

    when (cord){

            Pair(0,0) -> leftUpCorner()
            Pair(0, BOARD_DIM-1) -> rightUpCorner()
            Pair(BOARD_DIM-1,0) -> leftDownCorner()
            Pair(BOARD_DIM-1, BOARD_DIM-1) -> rightDownCorner()
            else -> {
                if (cord.first == 0){
                    upWall()
                    return
                }
                if(cord.second == 0){
                    leftWall()
                    return
                }
                if(cord.first == BOARD_DIM-1 ){
                    downWall()
                    return
                }
                if (cord.second == BOARD_DIM-1){
                    rightWall()
                    return
                }
                else {
                    innerWall()
                    return
                }
            }

    }
}

@Composable
fun up (){
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
fun down(){
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
fun left(){
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
fun right(){
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
fun rightDownCorner() {
    up()
    left()
}
@Composable
fun leftDownCorner() {
    up()
    right()
}
@Composable
fun rightUpCorner() {
    down()
    left()

}

@Composable
fun leftUpCorner (){
    down()
    right()

}

@Composable
fun innerWall() {
    up()
    down()
    left()
    right()
}
@Composable
fun rightWall() {
    up()
    down()
    left()
}
@Composable
fun downWall() {
    left()
    up()
    right()

}

@Composable
fun upWall(){
    left()
    right()
    down()

}

@Composable
fun leftWall() {
   up()
    down()
    right()
}






