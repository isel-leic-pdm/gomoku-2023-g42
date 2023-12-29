package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.login.LoginScreen
import com.example.gomoku.login.LoginScreenViewModel

class SignUpToLoginActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginScreenViewModel>()
    private val app by lazy { application as MenuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, SignUpToLoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
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

    private fun login (username: String, password: String){
        viewModel.loginUser(app.loginService, app.userInfoRepository,username, password)
    }
}

