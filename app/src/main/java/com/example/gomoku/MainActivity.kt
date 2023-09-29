package com.example.gomoku

import Cell
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomoku.model.BOARD_DIM
import com.example.gomoku.model.Player
import com.example.gomoku.ui.theme.GomokuTheme

/*const val BOARD_LINE_SIZE = 2
const val BOARD_CELL_SIZE = 64
const val AXIS_SIZE = 20*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    boardView(mapOf<Cell,Player>( Cell(2,2) to Player.WHITE) )
                }
            }
        }
    }
}

@Composable
fun authorScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontFamily = FontFamily.Serif, text = "Vasco Branco, number -> 48259")
        Text(fontFamily = FontFamily.Serif, text = "José Borges, number -> 48269")
        Text(fontFamily = FontFamily.Serif, text = "Sérgio Capela, number -> 46080")
        Button(onClick = { }) { Text(text = "Send a message to support the creators") }
    }
}

@Composable
fun homeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                fontFamily = FontFamily.Serif,
                text = "Gomoku",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { }) { Text(text = "Play") }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { }) { Text(text = "Authors") }
        }
    }
}

@Composable
fun boardView(boardState: Map<Cell, Player>) {

}

