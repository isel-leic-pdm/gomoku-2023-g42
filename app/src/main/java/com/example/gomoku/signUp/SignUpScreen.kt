package com.example.gomoku.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.user.NoUser
import com.example.gomoku.login.test
import com.example.gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    user: IOState<Any> = Idle,
    onSignUp: (String, String) -> Unit,
    onLoginSuccess : () -> Unit,
    setIdle: () -> Unit,
    onLoginRequested: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val confirmPopUp = remember { mutableStateOf(false) }
    val notMatchPass = remember { mutableStateOf(false) }
    val apiError = remember { mutableStateOf(false) }
    val blankUser = remember { mutableStateOf(false) }
    val invalidUsername = remember { mutableStateOf(false) }
    val blankPass = remember { mutableStateOf(false) }
    val invalidPass = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    GomokuTheme {
        Column {
            Button(
                onClick = {
                    scope.launch {
                        onLoginRequested()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )

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
            if (user is Idle) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Enter your credentials",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text(text = "Username") },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text(text = "Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = { confirmPassword.value = it },
                        label = { Text(text = "Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )


                    Button(
                        onClick = {
                            if (password.value == confirmPassword.value) {
                                onSignUp(username.value, confirmPassword.value)
                            } else notMatchPass.value = true
                        }
                    ) {
                        Text(text = "Sign Up")
                    }


                }
            }
            if (user is Loaded) {
                val loaded = user.result.getOrNull()

                if (loaded is NoUser) {
                    error.value = loaded.error
                    apiError.value = true
                    setIdle()
                } else {
                    confirmPopUp.value = true
                }

            }


            if (notMatchPass.value) {
                AlertDialog(
                    onDismissRequest = { notMatchPass.value = false },
                    title = { Text(text = "Passwords do not match!") },
                    text = { Text(text = "Insert matching passwords") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                notMatchPass.value = false
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
            if (confirmPopUp.value) {
                AlertDialog(
                    onDismissRequest = { confirmPopUp.value = false },
                    title = { Text(text = "User Created!") },
                    text = { Text(text = "Your user has been created successfully.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                confirmPopUp.value = false
                                onLoginSuccess()
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
            if (apiError.value) {
                AlertDialog(
                    onDismissRequest = { apiError.value = false },
                    title = { Text(text = "Error!") },
                    text = { Text(text = error.value) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                apiError.value = false
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )

            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun SignUpViewPreview(){

    SignUpScreen(Idle,::test, {} ,{}) {}


}