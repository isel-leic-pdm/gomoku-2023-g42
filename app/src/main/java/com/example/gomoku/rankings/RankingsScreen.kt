package com.example.gomoku.rankings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gomoku.domain.LoadState
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RankingScreen(
    rankings: LoadState,
    onFetch: () -> Unit,

    onHomeRequested: () -> Unit
)
{
    val scope = rememberCoroutineScope()





    GomokuTheme {
        Column {
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
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){


           onFetch()

            
            Spacer(modifier = Modifier.height(30.dp))
            
            if ( rankings is Loading) Text(text = "...LOADING...")
            if( rankings is Loaded<*>){

                Row {
                    Text("Position")
                    Text(text ="Username", Modifier.clickable {onHomeRequested()})
                    Text("Rank")

                }

                rankings.result.getOrNull()?.let {
                    Text(text = it as String)
                }
            }


        }


    }


}