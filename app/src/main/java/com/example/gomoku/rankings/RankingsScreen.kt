package com.example.gomoku.rankings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.domain.PlayerRank
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RankingScreen(
    rankings: IOState<Any>,
    onFetch: () -> Unit,
    onHomeRequested: () -> Unit
)
{
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    if(text.isBlank()) searchText = ""
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
            Spacer(modifier = Modifier.height(10.dp))
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                if(rankings is Idle) onFetch()
                if(rankings is Loading) CircularProgressIndicator()
                if(rankings is Loaded<*>){
                    Row (verticalAlignment = Alignment.CenterVertically){
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text(text = stringResource(id = R.string.username_label)) }
                        )
                        Button(onClick = { searchText = text}) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        }
                    }

                    RankBox(listOf(
                        stringResource(id = R.string.rank_label),
                        stringResource(id = R.string.username_label),
                        stringResource(id = R.string.games_label),
                        stringResource(id = R.string.point_label),
                        stringResource(id = R.string.wins_label),
                        stringResource(id = R.string.losses_label)
                        ),
                        20.dp )
                    Spacer(modifier = Modifier.height(20.dp))
                    rankings.result.getOrNull()?.let { list ->
                        list as List<*>
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            items(list){ r ->
                                r as PlayerRank

                                val playerInfo = listOf(
                                    r.rank,
                                    r.username,
                                    r.playedGames,
                                    r.points,
                                    r.wonGames,
                                    r.lostGames
                                )

                                if ( searchText.isBlank() || r.username.contains(searchText, ignoreCase = true)){
                                    RankBox(playerInfo)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RankBox(element: List<String>, spacer: Dp = 40.dp){
    var c = 1
    var w: Float

    Box (
        Modifier
            .width(500.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(8.dp),
            contentAlignment = Alignment.Center

    ){
        Row (
            horizontalArrangement = Arrangement.SpaceAround
        ){

            element.forEach {
                w = when (c) {
                    2 -> 2f
                    6 -> 0.3f
                    4 -> 1.3f
                    else -> 1f
                }
                Text(
                    text = it,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(w)
                )
                c++
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListRankingTest(){
    val ranking =  listOf("test", "1", "0", "0", "0")
    RankBox(ranking)
}