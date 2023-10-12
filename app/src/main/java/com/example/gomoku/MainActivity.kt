package com.example.gomoku

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gomoku.home.HomeScreen

import com.example.gomoku.ui.theme.GomokuTheme
import com.example.gomoku.ui.theme.*

/*const val BOARD_LINE_SIZE = 2
const val BOARD_CELL_SIZE = 64
const val AXIS_SIZE = 20*/

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AuthorScreen()
                    HomeScreen()
                    /*Column(modifier = Modifier.fillMaxSize()) {
                        val board = mutableStateOf(createBoard(Player.WHITE))
                        BoardRunView(board)
                    }

                     */




                }
            }
        }
    }
}





