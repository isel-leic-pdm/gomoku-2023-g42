package com.example.gomoku

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gomoku.about.AuthorToEmailActivity
import com.example.gomoku.ui.theme.GomokuTheme
import com.example.gomoku.ui.theme.*
import kotlinx.coroutines.launch

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
                    /*setContent{
                        homeScreen(
                            onInfoRequested = { AboutActivity.navigateTo(this) }
                        )
                    }*/
                    AuthorScreen(
                        onInfoRequested = { AuthorToEmailActivity.navigateTo(this) }
                    )

                    /*Column(modifier = Modifier.fillMaxSize()) {
                        val board = remember { mutableStateOf(createBoard(Player.WHITE)) }
                        BoardView(board)*/
                }
            }
        }
    }
}


@Composable
fun AuthorScreen(
    onInfoRequested: () -> Unit = {},
    openUrlRequested: (Uri) -> Unit = {},
    url: Uri = Uri.EMPTY
) {
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontFamily = FontFamily.Serif, text = "Vasco Branco, number -> 48259")
        Text(fontFamily = FontFamily.Serif, text = "José Borges, number -> 48269")
        Text(fontFamily = FontFamily.Serif, text = "Sérgio Capela, number -> 46080")
        Button(onClick = {
            scope.launch {
                onInfoRequested()
            }

        } ) { Text(text = "Send a message to support the creators") }
    }
}

@Composable
fun HomeScreen(onInfoRequested : () -> Unit) {
    val scope = rememberCoroutineScope()
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
            Button(onClick = {
                scope.launch {
                    onInfoRequested()
                }
            }) { Text(text = "Authors") }
        }
    }
}



