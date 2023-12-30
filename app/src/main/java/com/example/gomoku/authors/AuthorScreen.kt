package com.example.gomoku.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.gomoku.R
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@Composable
fun AuthorScreen(onInfoRequested: () -> Unit, onHomeRequested: ()-> Unit) {

    val scope = rememberCoroutineScope()
    GomokuTheme {

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

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(fontFamily = FontFamily.Serif, text = "Vasco Branco, ${stringResource(id =R.string.number_label )} -> 48259")
            Text(fontFamily = FontFamily.Serif, text = "José Borges, ${stringResource(id =R.string.number_label )} -> 48269")
            Text(fontFamily = FontFamily.Serif, text = "Sérgio Capela, ${stringResource(id =R.string.number_label )} -> 46080")
            Button(onClick = {
                scope.launch {
                    onInfoRequested()
                }
            }) { Text(text = stringResource(id = R.string.support_message)) }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthorScreenPrev(){

}

