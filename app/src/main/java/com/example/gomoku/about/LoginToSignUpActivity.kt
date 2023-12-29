package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gomoku.MainActivity
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.signUp.SignUpScreen
import com.example.gomoku.signUp.SignUpScreenViewModel

class LoginToSignUpActivity : ComponentActivity() {

    private val viewModel by viewModels<SignUpScreenViewModel>()
    private val app by lazy { application as MenuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginToSignUpActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        setContent {
            SignUpScreen(
                user = viewModel.user,
                onLoginSuccess = { MainActivity.navigateTo(this)},
                onSignUp = ::signUp,
                setIdle = { viewModel.setIdle() },
                onLoginRequested = { SignUpToLoginActivity.navigateTo(this) }
            )
        }
    }

    private fun signUp(username: String, password: String) {
        viewModel.createUser(app.signUpService, username, password)
    }
}

