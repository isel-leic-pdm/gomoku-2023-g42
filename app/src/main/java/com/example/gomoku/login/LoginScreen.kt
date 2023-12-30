package com.example.gomoku.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomoku.R
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    user: IOState<Any> = Idle,
    onHomeRequested: () -> Unit,
    onLogin: (String, String) -> Unit,
    onSignUp: () -> Unit,
    setIdle: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    GomokuTheme {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                if (user is Idle) {

                    OutlinedTextField(

                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text(text = stringResource(id = R.string.username_label)) },
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text(text = stringResource(id = R.string.password_label)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.width(30.dp))

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = {
                            scope.launch { onSignUp() }
                        })
                        { Text(stringResource(id = R.string.sign_up_label)) }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(onClick = {
                            scope.launch { onLogin(username.value, password.value) }

                        })
                        { Text(stringResource(id = R.string.login_label)) }
                    }
                }

                if (user is Loading) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (user is Loaded<*>) {
                    val loaded = user.result.getOrNull()
                    if (loaded is LoggedUser) onHomeRequested()
                    else {
                        AlertDialog(
                            onDismissRequest = {
                                setIdle()
                            },
                            title = { Text(text = stringResource(id = R.string.failed_log_label)) },
                            text = { Text(text = stringResource(id = R.string.failed_log_message)) },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        setIdle()
                                    }
                                ) {
                                    Text(stringResource(id = R.string.ok_button))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginViewPreview() {
    LoginScreen(Idle, {}, ::test, {}) { }
}

fun test(text: String, txt: String) {}


