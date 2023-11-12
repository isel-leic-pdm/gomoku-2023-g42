package com.example.gomoku.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomoku.R
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
   onHomeRequested : () -> Unit
) {
    val username = remember{mutableStateOf("")}
    val password = remember{mutableStateOf("")}
    val scope = rememberCoroutineScope()

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
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {username.value = it},
                    label = { Text(text = "Username")},
                )
                Spacer(modifier = Modifier.width(30.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    label = { Text(text = "Password")},
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.width(30.dp))
                //Text(text = "Sign in")
                Button(onClick = {
                    scope.launch {
                        onHomeRequested()
                    }

                }) {
                    Text("Sign in")
                }
            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun LoginViewPreview(){

}


