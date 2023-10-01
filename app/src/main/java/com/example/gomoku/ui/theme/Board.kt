package com.example.gomoku.ui.theme

import Cell
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomoku.model.Player
import com.example.gomoku.model.indexToColumn
import com.example.gomoku.model.toRow

val cellSize = 65.dp
val lineSize = 1.dp
@Composable
fun boardView(boardState: Map<Cell, Player?>) {
    Column{
        repeat(2){r->
            Row {
                repeat(2){ c->
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
    modifier: Modifier = Modifier
        .size(cellSize)
        .background(Color.DarkGray)
        .border(lineSize, Color.Black))
{
    Box(modifier = modifier, Alignment.Center){

        Canvas(Modifier){
            //seg esq
            drawLine(
                start = Offset(0f, 0f),

                end = Offset(-cellSize.value-20, 0f),
                color = Color.Yellow,
                strokeWidth = 5F
            )
            //seg cima
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(0f, -cellSize.value-20),
                color = Color.Blue,
                strokeWidth = 5F
            )
            //seg down
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(0f, (cellSize.value + 20)),
                color = Color.Red,
                strokeWidth = 5F
            )
            drawLine(
                start = Offset(0f, 0f),
                end = Offset((cellSize.value + 20) , 0f),
                color = Color.Green,
                strokeWidth = 5F
            )

        }
        Box(modifier = Modifier.size(cellSize / 4)
            .clickable(true) { {}}
            .background(Color.Cyan))

    }

}




