package com.example.gomoku.rankings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@Composable
fun RankingScreen(
    //onInfoRequested:(OkHttpClient, Gson) -> String,
    rankings: String,
    onHomeRequested: () -> Unit
)
{
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch{
                onHomeRequested()
            }
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )

    }
    GomokuTheme {

        Column {
            Text(rankings)
        }

    }


}