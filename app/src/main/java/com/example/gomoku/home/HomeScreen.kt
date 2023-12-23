package com.example.gomoku.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.demo.domain.BoardSize
import com.example.demo.domain.Rules
import com.example.demo.domain.Variant
import com.example.gomoku.R
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.ui.theme.GomokuTheme
import com.example.gomoku.user.User
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    user : User,
    onAuthorsRequested: () -> Unit,
    onRankingsRequested: () -> Unit
    //onLobbyRequested: (Int, String, String, LoggedUser) ->Unit
) {
    val scope = rememberCoroutineScope()
    var showBoardSizeDialog by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableIntStateOf(BoardSize.values()[0].size) }
    var selectedRules by remember { mutableStateOf(Rules.values()[0].string()) }
    var selectedVariant by remember { mutableStateOf(Variant.values()[0].string()) }

    GomokuTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontFamily = FontFamily.Serif,
                    text = (user as LoggedUser).username,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    fontFamily = FontFamily.Serif,
                    text = "Gomoku",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { showBoardSizeDialog = true })
                { Text(text = "Play") }

                if (showBoardSizeDialog) {
                    GameConfig(
                        onDismiss = { showBoardSizeDialog = false },
                        onConfirm = {
                            // TODO criar jogo com selectedSize, selectedRules, selectedVariant

                            //TODO onGameRequested(selectedSize, selectedRules, selectedVariant)
                        }
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    scope.launch {
                        onRankingsRequested()

                    }
                })
                { Text(text = "Rankings") }

                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    scope.launch {
                        onAuthorsRequested()
                    }
                })
                { Text(text = "Authors") }
            }
        }
    }
}


@Composable
fun GameConfig(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var boardSize by remember { mutableStateOf(BoardSize.values()[0])}
    var rules by remember {mutableStateOf(Rules.values()[0])}
    var variant by remember { mutableStateOf(Variant.values()[0]) }

    GomokuTheme {

        Dialog(onDismissRequest = onDismiss) {

            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Choose the board size: "
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = boardSize == BoardSize.SMALL,
                            onClick = { boardSize = BoardSize.SMALL },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = BoardSize.SMALL.string())

                        RadioButton(
                            selected = boardSize == BoardSize.BIG,
                            onClick = { boardSize = BoardSize.BIG },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = BoardSize.BIG.string())
                    }

                    Text(
                        text = "Choose the rules: "
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = rules == Rules.PRO,
                            onClick = {  rules = Rules.PRO },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = Rules.PRO.string())

                        RadioButton(
                            selected = rules == Rules.PRO_LONG,
                            onClick = { rules = Rules.PRO_LONG },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = Rules.PRO_LONG.string())
                    }

                    Text(
                        text = "Choose the gameplay variant: "
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = variant == Variant.FREESTYLE,
                            onClick = {  variant = Variant.FREESTYLE },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = Variant.FREESTYLE.string())

                        RadioButton(
                            selected = variant == Variant.SWAP,
                            onClick = {  variant = Variant.SWAP },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = Variant.SWAP.string())
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }

                        Button(onClick = {

                            onConfirm()
                        }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeViewPreview(){
    HomeScreen(LoggedUser("",""),{}){}
}