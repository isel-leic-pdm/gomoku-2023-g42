package com.example.gomoku

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gomoku.about.LoginToHomeActivity
import com.example.gomoku.about.LoginToSignUpActivity
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.login.LoginScreen
import com.example.gomoku.login.LoginScreenViewModel
import com.example.gomoku.ui.theme.GomokuTheme
import com.example.gomoku.ui.theme.*

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LoginScreenViewModel>()
    private val app by lazy { application as MenuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, MainActivity::class.java)
            origin.startActivity(intent)
        }
    }

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    setContent {
                        LoginScreen(
                            onHomeRequested = { LoginToHomeActivity.navigateTo(this) },
                            onSignUp = {LoginToSignUpActivity.navigateTo(this)},
                            onLogin = ::login,
                            setIdle = {viewModel.setIdle()},
                            user = viewModel.user
                        )
                    }
                }
            }
        }

    }

    private fun login (username: String, password: String){
        viewModel.loginUser(app.loginService, app.userInfoRepository, username, password)
    }
}








