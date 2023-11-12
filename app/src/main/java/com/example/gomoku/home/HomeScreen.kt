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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gomoku.R
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onAuthorsRequested: () -> Unit,
    onRankingsRequested: () -> Unit
    //onGameRequested: (Int, String, String) ->Unit
) {
    val scope = rememberCoroutineScope()
    var showBoardSizeDialog by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableIntStateOf(BoardSize.FIFTEEN.value()) }
    var selectedRules by remember { mutableStateOf(Rules.PRO.toString()) }
    var selectedVariant by remember { mutableStateOf(Variant.FREESTYLE.toString()) }

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
                }) { Text(text = "Rankings") }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    scope.launch {
                        onAuthorsRequested()
                    }
                }) { Text(text = "Authors") }
            }
        }
    }
}
enum class BoardSize{
    FIFTEEN,
    NINETEEN;

    @Override
    override fun toString(): String {
        return if (this == FIFTEEN) "15" else "19"
    }
    fun value(): Int = if (this == FIFTEEN) 15 else 19
}

enum class Rules{
    PRO,
    PRO_LONG;

    @Override
    override fun toString(): String {
        return if (this == PRO) "Pro" else "Pro Long"
    }
}

enum class Variant{
    FREESTYLE,
    SWAP;

    @Override
    override fun toString(): String {
        return if (this == FREESTYLE) "Freestyle" else "Swap"
    }
}

@Composable
fun GameConfig(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var boardSize by remember { mutableStateOf(BoardSize.FIFTEEN) }
    var rules by remember { mutableStateOf(Rules.PRO) }
    var variant by remember { mutableStateOf(Variant.FREESTYLE) }


    Dialog(onDismissRequest = onDismiss) {
        // Dialog content
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

                // Radio buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = boardSize == BoardSize.FIFTEEN,
                        onClick = { boardSize = BoardSize.FIFTEEN },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = BoardSize.FIFTEEN.toString())

                    RadioButton(
                        selected = boardSize == BoardSize.NINETEEN,
                        onClick = { boardSize = BoardSize.NINETEEN },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = BoardSize.NINETEEN.toString())
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
                    Text(text = Rules.PRO.toString())

                    RadioButton(
                        selected = rules == Rules.PRO_LONG,
                        onClick = { rules = Rules.PRO_LONG },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = Rules.PRO_LONG.toString())
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
                    Text(text = Variant.FREESTYLE.toString())

                    RadioButton(
                        selected = variant == Variant.SWAP,
                        onClick = {  variant = Variant.SWAP },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = Variant.SWAP.toString())
                }

                // Buttons for confirmation and dismissal
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
                        // Pass the selected option to the callback
                        onConfirm()
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

/*@Preview(showSystemUi = true)
@Composable
fun HomeViewPreview(){
    HomeView()
}*/