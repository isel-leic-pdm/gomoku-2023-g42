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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gomoku.model.BoardSize
import com.example.gomoku.model.Rules
import com.example.gomoku.model.Variant
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Loaded
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.ui.theme.GomokuTheme
import com.example.gomoku.user.User
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onAuthorsRequested: () -> Unit,
    onRankingsRequested: () -> Unit,
    userInfo: IOState<Pair<User, LobbyInfo>>,
    getUser: () -> Unit,
    onUpdateLobby: (String, String, String) -> Unit,
    error: IOState<String>,
    onDismiss: () -> Unit = {},
    onLobbyRequested: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var showLobbySettings by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf(BoardSize.values()[0]) }
    var selectedRules by remember { mutableStateOf(Rules.values()[0]) }
    var selectedVariant by remember { mutableStateOf(Variant.values()[0]) }

    getUser()

    if (error is Loaded) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = stringResource(id = R.string.general_error_label)) },
            text = { Text(text = error.result.getOrNull() ?: stringResource(id = R.string.general_error_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok_button))
                }
            }
        )
    }

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

                if ((userInfo is Loaded) && (userInfo.result.getOrNull() != null)) {
                    Text(
                        fontFamily = FontFamily.Serif,
                        text = stringResource(id = R.string.welcome) + (userInfo.result.getOrNull()?.first as LoggedUser).username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    fontFamily = FontFamily.Serif,
                    text = stringResource(id = R.string.app_name),
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
                Button(onClick = { showLobbySettings = true })
                { Text(text = stringResource(id = R.string.play_button)) }

                if (showLobbySettings) {
                    Dialog(onDismissRequest = { showLobbySettings = false  }) {

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
                                    text = stringResource(id = R.string.choose_size_label)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedSize == BoardSize.SMALL,
                                        onClick = { selectedSize = BoardSize.SMALL },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = BoardSize.SMALL.string())

                                    RadioButton(
                                        selected = selectedSize == BoardSize.BIG,
                                        onClick = { selectedSize = BoardSize.BIG },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = BoardSize.BIG.string())
                                }

                                Text(
                                    text = stringResource(id = R.string.choose_rules_label)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedRules == Rules.PRO,
                                        onClick = { selectedRules = Rules.PRO },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = Rules.PRO.string())

                                    RadioButton(
                                        selected = selectedRules == Rules.LONG_PRO,
                                        onClick = { selectedRules = Rules.LONG_PRO },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = Rules.LONG_PRO.string())
                                }

                                Text(
                                    text = stringResource(id = R.string.choose_variant_label)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedVariant == Variant.FREESTYLE,
                                        onClick = { selectedVariant = Variant.FREESTYLE },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = Variant.FREESTYLE.string())

                                    /*RadioButton(
                                        selected = selectedVariant == Variant.SWAP,
                                        onClick = { selectedVariant = Variant.SWAP },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = stringResource(id = R.string.swap_label))*/
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(onClick = { showLobbySettings = false }) {
                                        Text(stringResource(id = R.string.cancel_button ))
                                    }

                                    Button(onClick = {
                                        onUpdateLobby(selectedRules.string(),selectedVariant.string(),selectedSize.string())
                                        onLobbyRequested()
                                    }) {
                                        Text(stringResource(id = R.string.join_button))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    scope.launch {
                        onRankingsRequested()
                    }
                })
                { Text(text = stringResource(id = R.string.rankings_button)) }

                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    scope.launch {
                        onAuthorsRequested()
                    }
                })
                { Text(text = stringResource(id = R.string.authors_button)) }
            }
        }
    }
}